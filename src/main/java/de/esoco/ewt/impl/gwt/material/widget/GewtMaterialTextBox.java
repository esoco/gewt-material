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
package de.esoco.ewt.impl.gwt.material.widget;

import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.MaterialTextBox;

import de.esoco.ewt.component.TextControl.IsTextControlWidget;
import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;

/**
 * A {@link MaterialTextBox} subclass that also implements the interface
 * {@link IsTextControlWidget}.
 *
 * @author eso
 */
public class GewtMaterialTextBox extends MaterialTextBox
	implements IsTextControlWidget, ImageAttribute {

	private ImageAttributeMixin<GewtMaterialTextBox> imageAttrMixin =
		new ImageAttributeMixin<>(this);

	@Override
	public int getCursorPos() {
		return asValueBoxBase().getCursorPos();
	}

	@Override
	public Image getImage() {
		return imageAttrMixin.getImage();
	}

	@Override
	public void setImage(Image image) {
		imageAttrMixin.setImage(image);
	}

	/**
	 * @see MaterialTextBox#setType(InputType)
	 */
	@Override
	public void setType(InputType type) {
		super.setType(type);

		// workaround for https://github.com/GwtMaterialDesign/gwt-material/issues/424
		asValueBoxBase().removeStyleName("validate");
	}

	@Override
	public void setVisibleLength(int columns) {
		// not supported by base class
	}
}
