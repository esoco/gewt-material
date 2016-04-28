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
package de.esoco.ewt.impl.gwt.material;

import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.TextComponent.IsTextBox;
import de.esoco.ewt.component.TextField.TextFieldWidgetFactory;
import de.esoco.ewt.impl.gwt.material.MaterialTextBoxFactory.GewtMaterialTextBox;
import de.esoco.ewt.style.StyleData;
import de.esoco.ewt.style.StyleFlag;


/********************************************************************
 * The factory for {@link MaterialButton} widgets.
 *
 * @author eso
 */
public class MaterialTextBoxFactory
	extends TextFieldWidgetFactory<GewtMaterialTextBox>
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public GewtMaterialTextBox createWidget(
		Component rComponent,
		StyleData rStyle)
	{
		GewtMaterialTextBox aTextBox = new GewtMaterialTextBox();

		if (rStyle.hasFlag(StyleFlag.PASSWORD))
		{
			aTextBox.setType(InputType.PASSWORD);
		}

		return aTextBox;
	}

	//~ Inner Classes ----------------------------------------------------------

	/********************************************************************
	 * A {@link MaterialTextBox} subclass that also implements the interface
	 * {@link IsTextBox}.
	 *
	 * @author eso
	 */
	static class GewtMaterialTextBox extends MaterialTextBox
		implements IsTextBox
	{
		//~ Methods ------------------------------------------------------------

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
}
