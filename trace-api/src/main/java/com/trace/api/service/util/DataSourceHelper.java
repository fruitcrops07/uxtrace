package com.trace.api.service.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trace.api.ApplicationConfig;
import com.trace.api.model.Trace;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

@Component
public class DataSourceHelper {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceHelper.class);

    @Autowired
    private ApplicationConfig appConfig;

    private static final int CONNECTION_TIMEOUT = 500;

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public Trace create(Trace trace) {
        Trace createdTrace = null;
        String requestObject = DataSourceHelper.createKDE(trace).toString();
        OkHttpClient client = DataSourceHelper.createClient();
        RequestBody body = RequestBody.create(JSON, requestObject);
        Request request = new Request.Builder().url(appConfig.getDatasouceUrl() + "/CreateKDE").post(body).build();
        logger.info("action=create, message=executing_blockchain_api_request");
        try (Response response = client.newCall(request).execute()) {
            createdTrace = parseKDE(response.body().string());
        } catch (IOException e) {
            logger.info("action=create, message=IOException_occured, error_message={}", e.getLocalizedMessage());
        }
        return createdTrace;

    }

    public Trace findByCode(String code) {
        Trace record = null;
        OkHttpClient client = DataSourceHelper.createClient();
        final String url = appConfig.getDatasouceUrl() + "/KDE";
        HttpUrl.Builder httBuilder = HttpUrl.parse(url).newBuilder();

        httBuilder.addQueryParameter("filter", "{\"where\":{\"code\":\"" + code + "\"}}");
        Request request = new Request.Builder().url(httBuilder.build()).get().build();
        try (Response response = client.newCall(request).execute()) {
            List<Trace> resultSet = parseKDEArray(response.body().string());
            record = resultSet.size() > 0 ? resultSet.get(0) : null;
        } catch (IOException e) {
            logger.info("action=findCode, message=finding_trace_record_by_code, code={}", code);
        }
        return record;
    }

    public List<Trace> findAll() {
        List<Trace> list = new ArrayList<Trace>();
        OkHttpClient client = DataSourceHelper.createClient();
        Request request = new Request.Builder().url(appConfig.getDatasouceUrl() + "/CreateKDE").get().build();
        try (Response response = client.newCall(request).execute()) {
            JSONArray array = new JSONArray(response.body().string());
            array.forEach(obj -> list.add(parseKDE(obj.toString())));
        } catch (IOException e) {
            logger.info("action=findAll, message=fetching_all_trace_record");
        }
        return list;
    }

    private static OkHttpClient createClient() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        return new OkHttpClient.Builder().addInterceptor(logger).readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS).build();
    }

    private static JSONObject createKDE(Trace trace) {
        JSONObject object = new JSONObject();
        object.append("$class", "com.altermyth.mynetwork.CreateKDE");
        object.append("code", trace.getGtin() + trace.getBatchOrLot());
        object.append("species", trace.getSpecies());
        object.append("byCatch", trace.isByCatch());
        object.append("production", ProductionCode.getByCodeValue(trace.getProduction()).getNumberValue());
        return object;
    }

    private static Trace parseKDE(String kde) {
        JSONObject responseObject = new JSONObject(kde);
        return new Trace((responseObject.getString("code").length() < 12) ? responseObject.getString("code")
                                                                          : responseObject.getString("code").substring(0, 12),
                         (responseObject.getString("code").length() == 33)
                                                                           ? responseObject.getString("code")
                                                                                           .substring(13,
                                                                                                      responseObject.getString("code").length() - 1)
                                                                           : responseObject.getString("code"),
                         ProductionCode.getByNumberValue(responseObject.getInt("production")).getCodeValue(),
                         responseObject.getString("species"),
                         responseObject.getBoolean("byCatch"));
    }

    private static List<Trace> parseKDEArray(String kdeArray) {
        List<Trace> traces = new ArrayList<Trace>();
        JSONArray array = new JSONArray(kdeArray);
        array.forEach(responseObject -> {
            JSONObject object = (JSONObject)responseObject;
            traces.add(new Trace((object.getString("code").length() < 12) ? object.getString("code") : object.getString("code").substring(0, 12),
                                 (object.getString("code").length() == 33) ? object.getString("code").substring(12, object.getString("code").length())
                                                                           : object.getString("code"),
                                 object.getString("species"),
                                 ProductionCode.getByNumberValue(object.getInt("production")).getCodeValue(),
                                 object.getBoolean("byCatch")));
        });
        return traces;
    }

    enum ProductionCode {
                         FIRST("01", 1),
                         SECOND("02", 2),
                         THIRD("03", 4),
                         FOURT("04", 4);
        private String codeValue;
        private Integer numberValue;

        public String getCodeValue() {
            return codeValue;
        }

        private ProductionCode(String codeValue, Integer numberValue) {
            this.codeValue = codeValue;
            this.numberValue = numberValue;
        }

        public Integer getNumberValue() {
            return numberValue;
        }

        public static ProductionCode getByCodeValue(String codeValue) {
            for (ProductionCode code : ProductionCode.values()) {
                if (code.getCodeValue().equals(codeValue))
                    return code;
            }
            throw new RuntimeException("No Valid value");
        }

        public static ProductionCode getByNumberValue(int numberCode) {
            for (ProductionCode code : ProductionCode.values()) {
                if (code.getNumberValue() == numberCode)
                    return code;
            }
            throw new RuntimeException("No Valid value");
        }
    }

}
