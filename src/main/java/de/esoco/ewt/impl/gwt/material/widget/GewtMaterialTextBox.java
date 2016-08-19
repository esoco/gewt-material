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

import gwt.material.design.client.ui.MaterialTextBox;

import de.esoco.ewt.component.TextControl.IsTextControlWidget;
import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;


/********************************************************************
 * A {@link MaterialTextBox} subclass that also implements the interface {@link
 * IsTextControlWidget}.
 *
 * @author eso
 */
public class GewtMaterialTextBox extends MaterialTextBox
	implements IsTextControlWidget, ImageAttribute
{
	//~ Instance fields --------------------------------------------------------

	private ImageAttributeMixin<GewtMaterialTextBox> aImageAttrMixin =
		new ImageAttributeMixin<>(this);

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getCursorPos()
	{
		return asGwtValueBoxBase().getCursorPos();
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
	public String getSelectedText()
	{
		return asGwtValueBoxBase().getSelectedText();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadOnly()
	{
		return asGwtValueBoxBase().isReadOnly();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setCursorPos(int nPosition)
	{
		asGwtValueBoxBase().setCursorPos(nPosition);
	}

	/***************************************
	 * Overridden to remove the invalid style which is set under certain
	 * conditions on Chrome and IE.
	 *
	 * @see MaterialTextBox#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean bFocused)
	{
		super.setFocus(bFocused);

		asGwtValueBoxBase().getElement().removeClassName("invalid");
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
		asGwtValueBoxBase().setReadOnly(bReadOnly);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setSelectionRange(int nStart, int nLength)
	{
		asGwtValueBoxBase().setSelectionRange(nStart, nLength);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setVisibleLength(int nColumns)
	{
	}
}
