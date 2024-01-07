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
package de.esoco.ewt.impl.gwt.material.widget;

import gwt.material.design.client.ui.MaterialImage;

import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.graphics.ImageRef;
import de.esoco.ewt.property.ImageAttribute;

import com.google.gwt.user.client.ui.HasText;

/**
 * A {@link MaterialImage} subclass that also implements {@link HasText} and
 * {@link ImageAttribute}.
 *
 * @author eso
 */
public class GewtMaterialImage extends MaterialImage
	implements HasText, ImageAttribute {

	private Image rImage;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage() {
		return rImage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText() {
		return getCaption();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setImage(Image rImage) {
		this.rImage = rImage;

		if (rImage instanceof ImageRef) {
			setUrl(((ImageRef) rImage).getGwtImage().getUrl());
		}
	}

	/**
	 * Overridden to prevent error "this$static_0_g$ is null" on updating the
	 * label. It occurs because
	 * {@link gwt.material.design.client.base.AbstractButton} always removes
	 * the
	 * text span in the
	 * {@link gwt.material.design.client.base.AbstractButton#setText(String)}
	 * method.
	 *
	 * @see HasText#setText(String)
	 */
	@Override
	public void setText(String sText) {
		setCaption(sText);
	}
}
