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

import gwt.material.design.client.base.HasIcon;

import de.esoco.ewt.GewtMaterial;
import de.esoco.ewt.graphics.Icon;
import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;

import com.google.gwt.user.client.ui.Widget;

/**
 * A mixin for GEWT extensions of GwtMaterial widgets that applies images to
 * Gwt
 *
 * @author eso
 */
public class ImageAttributeMixin<T extends Widget & HasIcon>
	implements ImageAttribute {

	private T widget;

	private Image image;

	/**
	 * Creates a new instance.
	 *
	 * @param widget The widget to provide the image attribute for
	 */
	public ImageAttributeMixin(T widget) {
		this.widget = widget;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void setImage(Image image) {
		this.image = image;

		if (image instanceof Icon) {
			GewtMaterial.applyIconType(((Icon) image).getName(), widget);
		}
	}
}
