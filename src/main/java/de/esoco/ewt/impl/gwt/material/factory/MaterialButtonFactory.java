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

import gwt.material.design.client.base.AbstractButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLink;

import de.esoco.ewt.component.Button.ButtonWidgetFactory;
import de.esoco.ewt.component.Component;
import de.esoco.ewt.style.StyleData;
import de.esoco.ewt.style.StyleFlag;

import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * The factory for {@link MaterialButton} widgets.
 *
 * @author eso
 */
public class MaterialButtonFactory<W extends Widget & Focusable & HasText>
	extends ButtonWidgetFactory<W>
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public W createWidget(Component rComponent, StyleData rStyle)
	{
		AbstractButton aButtonWidget;

		if (rStyle.hasFlag(StyleFlag.HYPERLINK))
		{
			aButtonWidget = new GewtMaterialLink();
		}
		else
		{
			aButtonWidget = new GewtMaterialButton();
		}

		return (W) aButtonWidget;
	}

	//~ Inner Classes ----------------------------------------------------------

	/********************************************************************
	 * A {@link MaterialButton} subclass that also implements {@link HasText}.
	 *
	 * @author eso
	 */
	static class GewtMaterialButton extends MaterialButton implements HasText
	{
	}

	/********************************************************************
	 * A {@link MaterialLink} subclass that also implements {@link HasText}.
	 *
	 * @author eso
	 */
	static class GewtMaterialLink extends MaterialLink implements HasText
	{
	}
}
