//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2018 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.Orientation;

import static de.esoco.lib.property.StyleProperties.ORIENTATION;


/********************************************************************
 * A layout implementation that creates and manages a {@link MaterialStepper}.
 *
 * @author eso
 */
public class MaterialStackPanelLayout
	extends MaterialSwitchPanelLayout<MaterialStepper, MaterialStep>
{
	//~ Instance fields --------------------------------------------------------

	private MaterialStepper aStepper;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addPage(Component rStepComponent,
						String    sStepTitle,
						boolean   bCloseable)
	{
		MaterialStep aStep = new MaterialStep();

		aStepper.add(aStep);
		aStep.add(rStepComponent.getWidget());
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public MaterialStepper createPanelWidget(
		Container rContainer,
		StyleData rStyle)
	{
		aStepper = new MaterialStepper();

		aStepper.setAxis(rStyle.getProperty(ORIENTATION, null) ==
						 Orientation.VERTICAL ? Axis.VERTICAL
											  : Axis.HORIZONTAL);

		return aStepper;
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
