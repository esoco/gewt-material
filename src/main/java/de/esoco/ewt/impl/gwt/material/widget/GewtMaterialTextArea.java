//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2017 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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

import gwt.material.design.client.ui.MaterialTextArea;

import de.esoco.ewt.component.TextArea.IsTextArea;
import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;

/**
 * A {@link MaterialTextArea} subclass that also implements the interface
 * {@link IsTextArea}.
 *
 * @author eso
 */
public class GewtMaterialTextArea extends MaterialTextArea
	implements IsTextArea, ImageAttribute {

	private ImageAttributeMixin<GewtMaterialTextArea> imageAttrMixin =
		new ImageAttributeMixin<>(this);

	/**
	 * Creates a new instance.
	 */
	public GewtMaterialTextArea() {
		setResizeRule(ResizeRule.AUTO);
	}

	@Override
	public int getCursorPos() {
		return asValueBoxBase().getCursorPos();
	}

	@Override
	public Image getImage() {
		return imageAttrMixin.getImage();
	}

	@Override
	public void setCharacterWidth(int columns) {
		// not supported by base class
	}

	@Override
	public void setImage(Image image) {
		imageAttrMixin.setImage(image);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);

		// fix for gwt-material issue
		// https://github.com/GwtMaterialDesign/gwt-material/issues/595
		getValueBoxBase().setReadOnly(readOnly);
	}

	@Override
	public void setVisibleLength(int columns) {
		// not supported by base class
	}

	@Override
	public void setVisibleLines(int rows) {
		// not supported by base class
	}
}
