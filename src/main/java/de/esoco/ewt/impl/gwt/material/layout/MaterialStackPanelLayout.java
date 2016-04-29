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

import gwt.material.design.addins.client.stepper.MaterialStepper;
import gwt.material.design.client.constants.Axis;

import de.esoco.ewt.UserInterfaceContext;
import de.esoco.ewt.component.SplitPanel.SplitPanelLayout;
import de.esoco.ewt.style.StyleData;
import de.esoco.ewt.style.StyleFlag;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * A layout implementation that creates and manages a {@link MaterialStepper}.
 *
 * @author eso
 */
public class MaterialStackPanelLayout extends SplitPanelLayout
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(HasWidgets rContainer,
						  Widget	 rWidget,
						  StyleData  rStyleData)
	{
		MaterialStepper rStepper = (MaterialStepper) rContainer;

		rStepper.add(rWidget);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public HasWidgets createLayoutContainer(
		UserInterfaceContext rContext,
		StyleData			 rStyle)
	{
		MaterialStepper aStepper = new MaterialStepper();

		aStepper.setAxis(rStyle.hasFlag(StyleFlag.VERTICAL) ? Axis.VERTICAL
															: Axis.HORIZONTAL);

		return aStepper;
	}
}
