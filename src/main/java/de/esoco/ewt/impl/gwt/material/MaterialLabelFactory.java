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
package de.esoco.ewt.impl.gwt.material;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialTitle;

import de.esoco.ewt.component.Label.LabelWidgetFactory;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.UserInterfaceProperties.LabelStyle;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * The factory for {@link MaterialButton} widgets.
 *
 * @author eso
 */
public class MaterialLabelFactory<W extends Widget & HasText>
	extends LabelWidgetFactory<W>
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	protected Widget createLabelWidget(LabelStyle eLabelStyle, StyleData rStyle)
	{
		Widget aWidget;

		switch (eLabelStyle)
		{
			case DEFAULT:
				aWidget = new MaterialLabel();
				break;

			case BRAND:
				aWidget = new MaterialNavBrand();
				break;

			case TITLE:
				aWidget = new MaterialTitle();
				break;

			default:
				aWidget = super.createLabelWidget(eLabelStyle, rStyle);
		}

		return aWidget;
	}
}
