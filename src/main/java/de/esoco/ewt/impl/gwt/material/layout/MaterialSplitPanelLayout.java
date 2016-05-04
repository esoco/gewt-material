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

import gwt.material.design.addins.client.splitpanel.MaterialSplitPanel;
import gwt.material.design.client.constants.Axis;
import gwt.material.design.client.ui.MaterialLabel;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.component.SplitPanel.SplitPanelLayout;
import de.esoco.ewt.style.StyleData;
import de.esoco.ewt.style.StyleFlag;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * A layout implementation that creates and manages a {@link
 * MaterialSplitPanel}.
 *
 * @author eso
 */
public class MaterialSplitPanelLayout extends SplitPanelLayout
{
	//~ Instance fields --------------------------------------------------------

	private MaterialSplitPanel aSplitPanel;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(HasWidgets rContainer,
						  Widget	 rWidget,
						  StyleData  rStyleData)
	{
//			Alignment eVerticalAlign = rStyleData.getVerticalAlignment();
//
//			if (eVerticalAlign == Alignment.BEGIN ||
//				eVerticalAlign == Alignment.END)
//			{
//				rWidget.setHeight("100%");
//			}
//			else
//			{
//				rWidget.setWidth("100%");
//			}

//		aSplitPanel.add(rWidget);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public HasWidgets createLayoutContainer(
		Container rContainer,
		StyleData rStyle)
	{
		aSplitPanel = new MaterialSplitPanel();

		aSplitPanel.setAxis(rStyle.hasFlag(StyleFlag.VERTICAL)
							? Axis.VERTICAL : Axis.HORIZONTAL);

		aSplitPanel.add(new MaterialLabel("Left Widget"));
		aSplitPanel.add(new MaterialLabel("Right Widget"));
		aSplitPanel.setLeftMax(1000);
		aSplitPanel.setRightMax(1000);
		aSplitPanel.setHeight("500px");

		return aSplitPanel;
	}
}
