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
package de.esoco.ewt.impl.gwt.material.factory;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextArea;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.TextArea.IsTextArea;
import de.esoco.ewt.component.TextArea.TextAreaWidgetFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialTextAreaFactory.GewtMaterialTextArea;
import de.esoco.ewt.style.StyleData;


/********************************************************************
 * The factory for {@link MaterialButton} widgets.
 *
 * @author eso
 */
public class MaterialTextAreaFactory
	extends TextAreaWidgetFactory<GewtMaterialTextArea>
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public GewtMaterialTextArea createWidget(
		Component rComponent,
		StyleData rStyle)
	{
		return new GewtMaterialTextArea();
	}

	//~ Inner Classes ----------------------------------------------------------

	/********************************************************************
	 * A {@link MaterialTextArea} subclass that also implements the interface
	 * {@link IsTextArea}.
	 *
	 * @author eso
	 */
	static class GewtMaterialTextArea extends MaterialTextArea
		implements IsTextArea
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
		public void setCharacterWidth(int nColumns)
		{
			// TODO: check if possible with ValueBoxBase
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
			// TODO: check if possible with ValueBoxBase
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void setVisibleLines(int nRows)
		{
			// TODO: check if possible with ValueBoxBase
		}
	}
}
