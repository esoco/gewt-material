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

import gwt.material.design.client.ui.MaterialRadioButton;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.RadioButton;

/**
 * The factory for {@link MaterialRadioButton} widgets.
 *
 * @author eso
 */
public class MaterialRadioButtonFactory
	extends MaterialWidgetFactory<RadioButton> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RadioButton createMaterialWidget(Component rComponent,
		StyleData rStyle) {
		return new MaterialRadioButton();
	}
}
