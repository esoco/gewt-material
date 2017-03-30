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


/********************************************************************
 * A {@link MaterialTextArea} subclass that also implements the interface {@link
 * IsTextArea}.
 *
 * @author eso
 */
public class GewtMaterialTextArea extends MaterialTextArea
	implements IsTextArea, ImageAttribute
{
	//~ Instance fields --------------------------------------------------------

	private ImageAttributeMixin<GewtMaterialTextArea> aImageAttrMixin =
		new ImageAttributeMixin<>(this);

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getCursorPos()
	{
		return asValueBoxBase().getCursorPos();
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
	public void setCharacterWidth(int nColumns)
	{
		// not supported by base class
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setImage(Image rImage)
	{
		aImageAttrMixin.setImage(rImage);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setReadOnly(boolean bReadOnly)
	{
		super.setReadOnly(bReadOnly);

		// fix for gwt-material issue
		// https://github.com/GwtMaterialDesign/gwt-material/issues/595
		getValueBoxBase().setReadOnly(bReadOnly);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setVisibleLength(int nColumns)
	{
		// not supported by base class
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setVisibleLines(int nRows)
	{
		// not supported by base class
	}
}
