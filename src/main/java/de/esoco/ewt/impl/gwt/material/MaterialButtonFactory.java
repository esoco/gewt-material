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

import gwt.material.design.client.ui.MaterialButton;

import de.esoco.ewt.component.Button.ButtonWidgetFactory;
import de.esoco.ewt.component.Component;
import de.esoco.ewt.impl.gwt.material.MaterialButtonFactory.GewtMaterialButton;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.HasText;


/********************************************************************
 * The factory for {@link MaterialButton} widgets.
 *
 * @author eso
 */
public class MaterialButtonFactory
	extends ButtonWidgetFactory<GewtMaterialButton>
{
	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 */
	public MaterialButtonFactory()
	{
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public GewtMaterialButton createWidget(
		Component rComponent,
		StyleData rStyle)
	{
		return new GewtMaterialButton();
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
}
