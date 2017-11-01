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

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialTextBox;

import de.esoco.ewt.component.TextControl.IsTextControlWidget;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;


/********************************************************************
 * A {@link MaterialTextBox} subclass that also implements the interface {@link
 * IsTextControlWidget}.
 *
 * @author eso
 */
public class GewtMaterialSearch extends MaterialSearch
	implements IsTextControlWidget
{
	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 */
	public GewtMaterialSearch()
	{
		addCloseHandler(new CloseHandler<String>()
			{
				@Override
				public void onClose(CloseEvent<String> rEvent)
				{
					setValue("", true);
				}
			});

		getIconSearch().addMouseDownHandler(e ->
											{
												if (isActive())
												{
													close();
												}
												else
												{
													open();
												}
											});

		valueBoxBase.addFocusHandler(e -> setActive(true));
		valueBoxBase.addBlurHandler(e -> setActive(false));
	}

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
	 * @see MaterialSearch#setActive(boolean)
	 */
	@Override
	public void setActive(boolean bActive)
	{
		super.setActive(bActive);

		MaterialIcon rCloseIcon = getIconClose();

		rCloseIcon.setIconColor(null);
		rCloseIcon.setVisible(bActive);
		getIconSearch().setIconColor(null);
		setTextColor(null);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setVisibleLength(int nColumns)
	{
	}
}
