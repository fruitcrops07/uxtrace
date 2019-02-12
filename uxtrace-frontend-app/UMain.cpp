//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "UMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TfmMain *fmMain;
//---------------------------------------------------------------------------
__fastcall TfmMain::TfmMain(TComponent* Owner)
  : TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TfmMain::FormShow(TObject *Sender)
{
//  AnsiString str;

  cxByCatch->ItemIndex = 0;
  cxProd->ItemIndex = 0;
//  str = cxProd->Items->Strings[cxProd->ItemIndex];
  cxSpecies->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void __fastcall TfmMain::sbConvertClick(TObject *Sender)
{
  AnsiString str;

  meJSON->Lines->Strings[0] = AnsiString("JSON Format");
  meJSON->Lines->Strings[1] = AnsiString("FILE NAME - TRACEINFO.JS");
  meJSON->Lines->Strings[2] = AnsiString("{");
  meJSON->Lines->Strings[3] = AnsiString("\"TRACEINFO\":{\"TRACEABILITY\":\""
      + meGTIN->Text + meLot->Text + "\",");
  str = cxSpecies->Items->Strings[cxSpecies->ItemIndex];
  str = str.SubString(1,3);
  meJSON->Lines->Strings[4] = "\"SPECIES\":\"" + str + "\",";
  str = cxProd->Items->Strings[cxProd->ItemIndex];
  str = str.SubString(1,2);
  meJSON->Lines->Strings[5] = "\"PRODUCTION\":\"" + str + "\",";
//  meJSON->Lines->Strings[6] = "\"BYCATCH\":" + edByCatch->Text + "}";
  meJSON->Lines->Strings[6] = "\"BYCATCH\":" + cxByCatch->Text + "}";
  meJSON->Lines->Strings[7] = AnsiString("}");
}
//---------------------------------------------------------------------------

void __fastcall TfmMain::sbClearClick(TObject *Sender)
{
//  meJSON->Lines->Clear();
  for (int i = 0; i <= 7; i++) {
    meJSON->Lines->Strings[i] = "";
  }
}
//---------------------------------------------------------------------------
void __fastcall TfmMain::cxByCatchDropDown(TObject *Sender)
{
  cxByCatch->Text = cxByCatch->Items->Strings[cxByCatch->ItemIndex];
}
//---------------------------------------------------------------------------





