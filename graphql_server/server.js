var express = require('express');
var express_graphql = require('express-graphql');
var { buildSchema } = require('graphql');

var axios = require("axios");

const dataSource = "http://13.82.172.225:3000/api/CreateKDE";




// GraphQL schema
var schema = buildSchema(`
    type Query {
        trace : [Trace]
        specificSpecies(species: String!): [Trace]
        customQuery(gtin: String!, batchOrLot: String!, species: String, byCatch: Boolean, production : String) : [Trace]
        message : String
    },
    type Trace {
        gtin : String
        batchOrLot : String
        species : String
        byCatch : Boolean
        production : String
    }

`);

async function getByGtinAndBatchOrLot(args){
    console.log("fetching datasource");
    let gtin = args.gtin;
    let batchOrLot = args.batchOrLot;
    let data = await axios.get(dataSource);
    let formullatedData = [];
    data.data.forEach(element => {
        let gtinValue = (element.code.length < 12) ? element.code : element.code.substring(0, 12);
        let batchOrLot = (element.code.length === 33) ? element.code.substring(13, element.code.length) : "";
        let production = parseProduction(element.production);
        formullatedData.push({"gtin": gtinValue, "batchOrLot": batchOrLot, "species" : element.species, "byCatch": element.byCatch, "production": production});
    });
    
    return formullatedData;
}

async function getSpeciesBySpeciesCode(args){
    console.log("fetching datasource");
    let speciesCode = args.species;
    let data = await axios.get(dataSource);
    let formullatedData = [];
    data.data.forEach(element => {
        if(element.species === speciesCode){
            let gtinValue = (element.code.length < 12) ? element.code : element.code.substring(0, 12);
            let batchOrLot = (element.code.length === 33) ? element.code.substring(13, element.code.length) : "";
            let production = parseProduction(element.production);
            formullatedData.push({"gtin": gtinValue, "batchOrLot": batchOrLot, "species" : element.species, "byCatch": element.byCatch, "production": production});
        }
    });
    
    return formullatedData;
}

async function customQuery(args){
    console.log("fetching datasource");
    let species = args.species;
    let code = args.gtin + args.batchOrLot;
    let production = parseInt(args.production);
    let byCatch = args.byCatch;
    let data = await axios.get(dataSource);
    let formullatedData = [];
    data.data.forEach(element => {
        if(element.code === code && element.byCatch === byCatch){
            let gtinValue = (element.code.length < 12) ? element.code : element.code.substring(0, 12);
            let batchOrLot = (element.code.length > 20) ? element.code.substring(13, element.code.length) : "";
            let production = parseProduction(element.production);
            formullatedData.push({"gtin": gtinValue, "batchOrLot": batchOrLot, "species" : element.species, "byCatch": element.byCatch, "production": production});
        }
    });
    
    return formullatedData;
}

function parseProduction(prodCode){
    if(prodCode === 1) return "01";
    if(prodCode === 2) return "02";
    if(prodCode === 3) return "03";
    if(prodCode === 4) return "04";
}



// Root resolver
var root = {
    message: () => 'Hello World!',
    trace : getByGtinAndBatchOrLot,
    specificSpecies : getSpeciesBySpeciesCode,
    customQuery : customQuery
};

// Create an express server and a GraphQL endpoint
var app = express();
app.use('/graphql', express_graphql({
    schema: schema,
    rootValue: root,
    graphiql: true
}));
app.listen(4000, () => console.log('Express GraphQL Server Now Running On localhost:4000/graphql'));