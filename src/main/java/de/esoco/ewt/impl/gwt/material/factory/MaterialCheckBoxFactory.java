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
package de.esoco.ewt.impl.gwt.material.factory;

import gwt.material.design.client.ui.MaterialCheckBox;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialSwitch;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.CheckBoxStyle;

import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.StyleProperties.CHECK_BOX_STYLE;


/********************************************************************
 * The factory for {@link MaterialCheckBox} widgets.
 *
 * @author eso
 */
public class MaterialCheckBoxFactory<W extends Widget & Focusable & HasHTML & HasValue<Boolean>>
	extends MaterialWidgetFactory<W>
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public Widget createMaterialWidget(Component rComponent, StyleData rStyle)
	{
		if (rStyle.getProperty(CHECK_BOX_STYLE, null) == CheckBoxStyle.SWITCH)
		{
			return new GewtMaterialSwitch();
		}
		else
		{
			return new MaterialCheckBox();
		}
	}
}
