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

import gwt.material.design.client.ui.MaterialIcon;

import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;


/********************************************************************
 * A {@link MaterialIcon} subclass that also implements {@link HasText}.
 *
 * @author eso
 */
public class GewtMaterialIcon extends MaterialIcon implements HasHTML,
															  ImageAttribute
{
	//~ Instance fields --------------------------------------------------------

	private ImageAttributeMixin<GewtMaterialIcon> aImageAttrMixin =
		new ImageAttributeMixin<>(this);

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public String getHTML()
	{
		return getText();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage()
	{
		return aImageAttrMixin.getImage();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setHTML(String sHtml)
	{
		setText(sHtml);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setImage(Image rImage)
	{
		aImageAttrMixin.setImage(rImage);
	}
}
