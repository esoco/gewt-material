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

/**
 * A layout implementation that creates and manages a
 * {@link MaterialSplitPanel}.
 *
 * @author eso
 */
public class MaterialSplitPanelLayout extends SplitPanelLayout {

	private MaterialSplitPanel splitPanel;

	private MaterialPanel first;

	private MaterialPanel second;

	private int added = 0;

	@Override
	public void addWidget(HasWidgets container, Widget widget,
		StyleData style) {
		Alignment verticalAlign = style.getVerticalAlignment();

		if (added == 0) {
			first.add(widget);
		} else if (added == 1) {
			second.add(widget);
		} else {
			throw new IllegalStateException(
				"Only two children allowed in split panel");
		}

		added++;
	}

	@Override
	public HasWidgets createLayoutContainer(Container container,
		StyleData style) {
		splitPanel = new MaterialSplitPanel();

		boolean vertical =
			style.getProperty(ORIENTATION, null) == Orientation.VERTICAL;

		int thickness = style.getIntProperty(SPLITTER_SIZE, -1);

		splitPanel.setAxis(vertical ? Axis.VERTICAL : Axis.HORIZONTAL);

		if (thickness > 0) {
			splitPanel.setThickness(thickness);
		}

		first = new MaterialPanel();
		second = new MaterialPanel();

		splitPanel.add(first);
		splitPanel.add(second);

		if (vertical) {
			first.setWidth("100%");
			second.setWidth("100%");
		} else {
			first.setHeight("100%");
			second.setHeight("100%");
		}

		return splitPanel;
	}
}
