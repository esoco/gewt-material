//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2016 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//	  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
package de.esoco.ewt.impl.gwt.material.layout;

import gwt.material.design.addins.client.stepper.MaterialStep;
import gwt.material.design.addins.client.stepper.MaterialStepper;
import gwt.material.design.client.constants.Axis;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.component.StackPanel.StackPanelLayout;
import de.esoco.ewt.style.StyleData;
import de.esoco.ewt.style.StyleFlag;

import com.google.gwt.user.client.ui.HasWidgets;


/********************************************************************
 * A layout implementation that creates and manages a {@link MaterialStepper}.
 *
 * @author eso
 */
public class MaterialStackPanelLayout extends StackPanelLayout
{
	//~ Instance fields --------------------------------------------------------

	private MaterialStepper aStepper;
	private MaterialStep    aFirstStep;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addPage(Component rStepComponent,
						String    sStepTitle,
						boolean   bCloseable)
	{
		MaterialStep aStep = aFirstStep;

		if (aStep == null)
		{
			aStep = new MaterialStep();
			aStepper.add(aStep);
		}
		else
		{
			aFirstStep = null;
		}

		aStep.add(rStepComponent.getWidget());
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public HasWidgets createLayoutContainer(
		Container rContainer,
		StyleData rStyle)
	{
		aStepper   = new MaterialStepper();
		aFirstStep = new MaterialStep();

		boolean bVertical = rStyle.hasFlag(StyleFlag.VERTICAL);

		aStepper.setAxis(bVertical ? Axis.VERTICAL : Axis.HORIZONTAL);

		// necessary unless an init bug in MaterialStepper is fixed
		// https://github.com/GwtMaterialDesign/gwt-material-addins/issues/63
		aStepper.add(aFirstStep);

		return aStepper;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getPageCount()
	{
		return aStepper.getWidgetCount();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getPageIndex(Component rGroupComponent)
	{
		return aStepper.getWidgetIndex(rGroupComponent.getWidget());
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getSelectionIndex()
	{
		return aStepper.getCurrentStepIndex();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setPageTitle(int nIndex, String sTitle)
	{
		MaterialStep rStep = (MaterialStep) aStepper.getWidget(nIndex);

		rStep.setTitle(sTitle);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setSelection(int nIndex)
	{
		aStepper.goToStep(nIndex + 1);
	}
}
