//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop
USERES("jv01.res");
USEFORM("UMain.cpp", fmMain);
//---------------------------------------------------------------------------
WINAPI WinMain(HINSTANCE, HINSTANCE, LPSTR, int)
{
  try
  {
     Application->Initialize();
     Application->CreateForm(__classid(TfmMain), &fmMain);
     Application->Run();
  }
  catch (Exception &exception)
  {
     Application->ShowException(&exception);
  }
  return 0;
}
//---------------------------------------------------------------------------
