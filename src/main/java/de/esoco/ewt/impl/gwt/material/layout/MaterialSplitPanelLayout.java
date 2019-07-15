//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2019 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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
import gwt.material.design.client.ui.MaterialPanel;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.component.SplitPanel.SplitPanelLayout;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.Alignment;
import de.esoco.lib.property.Orientation;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.StyleProperties.ORIENTATION;
import static de.esoco.lib.property.StyleProperties.SPLITTER_SIZE;


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

	private MaterialPanel aFirst;
	private MaterialPanel aSecond;
	private int			  nAdded = 0;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(HasWidgets rContainer,
						  Widget	 rWidget,
						  StyleData  rStyle)
	{
		Alignment eVerticalAlign = rStyle.getVerticalAlignment();

		if (nAdded == 0)
		{
			aFirst.add(rWidget);
		}
		else if (nAdded == 1)
		{
			aSecond.add(rWidget);
		}
		else
		{
			throw new IllegalStateException(
				"Only two children allowed in split panel");
		}

		nAdded++;
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

		boolean bVertical =
			rStyle.getProperty(ORIENTATION, null) == Orientation.VERTICAL;

		int nThickness = rStyle.getIntProperty(SPLITTER_SIZE, -1);

		aSplitPanel.setAxis(bVertical ? Axis.VERTICAL : Axis.HORIZONTAL);

		if (nThickness > 0)
		{
			aSplitPanel.setThickness(nThickness);
		}

		aFirst  = new MaterialPanel();
		aSecond = new MaterialPanel();

		aSplitPanel.add(aFirst);
		aSplitPanel.add(aSecond);

		if (bVertical)
		{
			aFirst.setWidth("100%");
			aSecond.setWidth("100%");
		}
		else
		{
			aFirst.setHeight("100%");
			aSecond.setHeight("100%");
		}

		return aSplitPanel;
	}
}
