//---------------------------------------------------------------------------

#ifndef UMainH
#define UMainH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ExtCtrls.hpp>
#include <Mask.hpp>
#include <Buttons.hpp>
#include <jpeg.hpp>
#include <ComCtrls.hpp>
//---------------------------------------------------------------------------
class TfmMain : public TForm
{
__published:	// IDE-managed Components
  TMaskEdit *meGTIN;
  TImage *imBanner;
  TLabel *laGTIN;
  TLabel *laLot;
  TLabel *laSpecies;
  TLabel *laProd;
  TLabel *laByCatch;
  TMaskEdit *meLot;
  TComboBox *cxSpecies;
  TComboBox *cxProd;
  TSpeedButton *sbConvert;
  TMemo *meJSON;
  TSpeedButton *sbClear;
  TLabel *laOutput;
  TComboBox *cxByCatch;
  void __fastcall sbConvertClick(TObject *Sender);
  void __fastcall sbClearClick(TObject *Sender);
  void __fastcall cxByCatchDropDown(TObject *Sender);
  void __fastcall FormShow(TObject *Sender);
private:	// User declarations
public:		// User declarations
  __fastcall TfmMain(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TfmMain *fmMain;
//---------------------------------------------------------------------------
#endif
