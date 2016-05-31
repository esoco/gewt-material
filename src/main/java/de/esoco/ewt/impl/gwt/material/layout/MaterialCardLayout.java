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
package de.esoco.ewt.impl.gwt.material.layout;

import gwt.material.design.client.base.AbstractButton;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * GWT Material implementation for list-style layouts.
 *
 * @author eso
 */
public class MaterialCardLayout extends AbstractMaterialLayout
{
	//~ Instance fields --------------------------------------------------------

	private MaterialCardContent aCardContent;
	private MaterialCardAction  aCardAction;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(HasWidgets rContainer,
						  Widget	 rWidget,
						  StyleData  rStyleData,
						  int		 nIndex)
	{
		if (rWidget instanceof AbstractButton)
		{
			if (aCardAction == null)
			{
				aCardAction = new MaterialCardAction();
				aCardAction.add(rWidget);
				rWidget = aCardAction;
			}
			else
			{
				aCardAction.add(rWidget);
				rWidget = null;
			}
		}
		else if (!(rWidget instanceof MaterialCardContent) &&
				 !(rWidget instanceof MaterialCardAction))
		{
			if (aCardContent == null)
			{
				aCardContent = new MaterialCardContent();
				aCardContent.add(rWidget);
				rWidget = aCardContent;
			}
			else
			{
				aCardContent.add(rWidget);
				rWidget = null;
			}
		}

		if (rWidget != null)
		{
			super.addWidget(rContainer, rWidget, rStyleData, nIndex);
		}
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void clear(HasWidgets rContainer)
	{
		super.clear(rContainer);

		aCardContent = null;
		aCardAction  = null;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	protected MaterialWidget creatMaterialLayoutContainer(
		Container rContainer,
		StyleData rContainerStyle)
	{
		return new MaterialCard();
	}
}
