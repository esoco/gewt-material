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
package de.esoco.ewt.impl.gwt.material.factory;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.TextControl.IsTextControlWidget;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialSearch;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialTextBox;
import de.esoco.ewt.style.StyleData;
import de.esoco.lib.property.TextFieldStyle;
import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.MaterialButton;

import static de.esoco.lib.property.StyleProperties.TEXT_FIELD_STYLE;

/**
 * The factory for {@link MaterialButton} widgets.
 *
 * @author eso
 */
public class MaterialTextBoxFactory
	extends MaterialWidgetFactory<IsTextControlWidget> {

	@Override
	public IsTextControlWidget createMaterialWidget(Component component,
		StyleData style) {
		IsTextControlWidget textWidget;

		TextFieldStyle textFieldStyle =
			style.getProperty(TEXT_FIELD_STYLE, TextFieldStyle.DEFAULT);

		if (textFieldStyle == TextFieldStyle.SEARCH) {
			textWidget = new GewtMaterialSearch();
		} else {
			GewtMaterialTextBox textBox = new GewtMaterialTextBox();

			if (textFieldStyle == TextFieldStyle.PASSWORD) {
				textBox.setType(InputType.PASSWORD);
			}
			textWidget = textBox;
		}

		return textWidget;
	}
}
