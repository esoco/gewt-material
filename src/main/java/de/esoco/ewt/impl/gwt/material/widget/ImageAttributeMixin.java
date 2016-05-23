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

import gwt.material.design.addins.client.stepper.base.mixin.AbstractMixin;
import gwt.material.design.client.base.HasIcon;
import gwt.material.design.client.constants.IconType;

import de.esoco.ewt.graphics.Icon;
import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;

import com.google.gwt.user.client.ui.UIObject;


/********************************************************************
 * A mixin for GEWT extensions of GwtMaterial widgets that applies images to Gwt
 *
 * @author eso
 */
public class ImageAttributeMixin<T extends UIObject & HasIcon>
	extends AbstractMixin<T> implements ImageAttribute
{
	//~ Instance fields --------------------------------------------------------

	private Image rImage;

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	public ImageAttributeMixin(T rWidget)
	{
		super(rWidget);
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage()
	{
		return rImage;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setImage(Image rImage)
	{
		this.rImage = rImage;

		if (rImage instanceof Icon)
		{
			uiObject.setIconType(IconType.valueOf(((Icon) rImage).getName()));
		}
	}
}
