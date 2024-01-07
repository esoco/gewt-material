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

import de.esoco.ewt.component.Container;
import de.esoco.ewt.component.MainView.MainViewLayout;
import de.esoco.ewt.layout.DefaultLayoutFactory;
import de.esoco.ewt.layout.GenericLayout;
import de.esoco.ewt.layout.LayoutMapper;
import de.esoco.ewt.style.StyleData;
import de.esoco.lib.property.LayoutType;

import static de.esoco.lib.property.StyleProperties.USE_STANDARD_COMPONENT;

/**
 * A factory and mapper that creates layouts or maps GEWT layouts to new layouts
 * that are based on GwtMaterialDesign widgets.
 *
 * @author eso
 */
public class MaterialLayoutFactory extends DefaultLayoutFactory
	implements LayoutMapper {

	@Override
	public GenericLayout createLayout(Container parentContainer,
		StyleData containerStyle, LayoutType layoutType) {
		GenericLayout layout;

		if (containerStyle.hasFlag(USE_STANDARD_COMPONENT)) {
			return super.createLayout(parentContainer, containerStyle,
				layoutType);
		}

		switch (layoutType) {
			case SPLIT:
				layout = new MaterialSplitPanelLayout();
				break;

			case FLOW:
			case GRID:
				layout = new MaterialFlowLayout();
				break;

			case TABS:
				layout = new MaterialTabPanelLayout();
				break;

			case STACK:
				layout = new MaterialStackPanelLayout();
				break;

			case CARD:
				layout = new MaterialCardLayout();
				break;

			case GRID_ROW:
			case GRID_COLUMN:
				layout = new MaterialGridLayout(layoutType);
				break;

			case LIST:
				layout = new MaterialListLayout();
				break;

			case LIST_ITEM:
				layout = new MaterialListItemLayout();
				break;

			case MENU:
				layout = new MaterialMenuLayout();
				break;

			case HEADER:
			case CONTENT:
			case SECONDARY_CONTENT:
			case FOOTER:
				layout = new MaterialContentLayout(layoutType);
				break;

			default:
				layout = super.createLayout(parentContainer, containerStyle,
					layoutType);
		}

		return layout;
	}

	@Override
	public GenericLayout mapLayout(Container container, GenericLayout layout) {
		if (layout instanceof MainViewLayout) {
			layout = new MaterialMainViewLayout();
		}

		return layout;
	}
}
