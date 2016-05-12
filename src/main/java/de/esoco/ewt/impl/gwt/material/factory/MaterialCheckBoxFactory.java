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

import de.esoco.ewt.component.CheckBox.CheckBoxWidgetFactory;
import de.esoco.ewt.component.Component;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * The factory for {@link MaterialCheckBox} widgets.
 *
 * @author eso
 */
public class MaterialCheckBoxFactory<W extends Widget & Focusable & HasHTML & HasValue<Boolean>>
	extends CheckBoxWidgetFactory<W>
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public W createWidget(Component rComponent, StyleData rStyle)
	{
		return (W) new MaterialCheckBox();
	}
}
