//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2018 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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

import gwt.material.design.client.constants.CheckBoxType;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialSwitch;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.impl.gwt.HasEventHandlingDelay;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.CheckBoxStyle;

import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.StyleProperties.CHECK_BOX_STYLE;


/********************************************************************
 * The factory for {@link MaterialCheckBox} widgets.
 *
 * @author eso
 */
public class MaterialCheckBoxFactory<W extends Widget & Focusable & HasHTML & HasValue<Boolean>>
	extends MaterialWidgetFactory<W>
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public W createMaterialWidget(Component rComponent, StyleData rStyle)
	{
		CheckBoxStyle eStyle  = rStyle.getProperty(CHECK_BOX_STYLE, null);
		Widget		  aWidget;

		if (eStyle == CheckBoxStyle.SWITCH)
		{
			aWidget = new GewtMaterialSwitch();
		}
		else
		{
			MaterialCheckBox aCheckBox = new GewtMaterialCheckBox();

			if (eStyle == CheckBoxStyle.SOLID)
			{
				aCheckBox.setType(CheckBoxType.FILLED);
			}

			aWidget = aCheckBox;
		}

		return (W) aWidget;
	}

	//~ Inner Classes ----------------------------------------------------------

	/********************************************************************
	 * Subclass of {@link MaterialCheckBox} that implements {@link
	 * HasEventHandlingDelay}.
	 *
	 * @author eso
	 */
	public static class GewtMaterialCheckBox extends MaterialCheckBox
		implements HasEventHandlingDelay
	{
		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public int getEventHandlingDelay()
		{
			return 100;
		}
	}

	/********************************************************************
	 * A subclass of {@link MaterialSwitch} that implements the methods of
	 * {@link HasHTML} to set the switch label. It also implements {@link
	 * HasEventHandlingDelay}.
	 *
	 * @author eso
	 */
	public class GewtMaterialSwitch extends MaterialSwitch
		implements HasText, HasEventHandlingDelay
	{
		//~ Instance fields ----------------------------------------------------

		private String sText;

		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance.
		 */
		public GewtMaterialSwitch()
		{
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public int getEventHandlingDelay()
		{
			return 100;
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public String getText()
		{
			return sText;
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void setText(String sText)
		{
			super.setOnLabel(sText);
			this.sText = sText;
		}
	}
}
