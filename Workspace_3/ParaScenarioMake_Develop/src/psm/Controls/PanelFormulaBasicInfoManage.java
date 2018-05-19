package psm.Controls;

import psm.Models.BusinessModel.FormulaManage;

public class PanelFormulaBasicInfoManage {
	
   public void DoPlFormulaName_Action(String formulaName)
   {
	   FormulaManage.setFormula("Name",formulaName);
   }
   public void DoPlFormulaAuthor_Action(String formulaAuthor)
   {
	   FormulaManage.setFormula("Author",formulaAuthor);
   }
   public void DoPlFormulaGoal_Action(String formulaGoal)
   {
	   FormulaManage.setFormula("Goal",formulaGoal);
   }
   public void DoPlFormulaScale_Action(String formulaScale)
   {
	   FormulaManage.setFormula("Scale",formulaScale);
   }
   public void DoPlFormulaOutline_Action(String formulaOutline)
   {
	   FormulaManage.setFormula("Outline",formulaOutline);
   }
   
}
