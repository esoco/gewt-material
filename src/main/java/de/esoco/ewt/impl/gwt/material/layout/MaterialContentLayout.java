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

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialFooter;
import gwt.material.design.client.ui.MaterialHeader;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.Layout;

import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * GWT Material implementation for content-specific layouts.
 *
 * @author eso
 */
public class MaterialContentLayout extends AbstractMaterialLayout
{
	//~ Enums ------------------------------------------------------------------

	/********************************************************************
	 * Internal enumeration of the different content areas to consider.
	 */
	private enum ContentArea { GLOBAL, CARD, COLLAPSIBLE }

	//~ Instance fields --------------------------------------------------------

	private Layout eLayout;

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 *
	 * @param eLayout The layout of the content
	 */
	public MaterialContentLayout(Layout eLayout)
	{
		this.eLayout = eLayout;
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	protected MaterialWidget creatMaterialLayoutContainer(
		Container rContainer,
		StyleData rContainerStyle)
	{
		Widget		   rParentWidget = rContainer.getParent().getWidget();
		MaterialWidget aLayoutWidget = null;
		ContentArea    eContentArea;

		eContentArea = getContentArea(rParentWidget);

		switch (eContentArea)
		{
			case CARD:
				aLayoutWidget = createCardContentContainer(eLayout);
				break;

			case COLLAPSIBLE:
				aLayoutWidget = createCollapsibleContentContainer(eLayout);
				break;

			case GLOBAL:
				aLayoutWidget = createGlobalContentContainer(eLayout);
				break;
		}

		if (aLayoutWidget == null)
		{
			throw new IllegalArgumentException("Unsupported content layout " +
											   eLayout + " for " +
											   rParentWidget.getClass()
											   .getSimpleName());
		}

		return aLayoutWidget;
	}

	/***************************************
	 * Creates the content layout widgets for a {@link MaterialCard} container.
	 *
	 * @param  eLayout The content layout type
	 *
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createCardContentContainer(Layout eLayout)
	{
		MaterialWidget aLayoutWidget;

		switch (eLayout)
		{
			case CONTENT:
				aLayoutWidget = new MaterialCardContent();
				break;

			case FOOTER:
				aLayoutWidget = new MaterialCardAction();
				break;

			default:
				aLayoutWidget = null;
		}

		return aLayoutWidget;
	}

	/***************************************
	 * Creates the content layout widgets for a {@link MaterialCollapsible}
	 * container.
	 *
	 * @param  eLayout The content layout type
	 *
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createCollapsibleContentContainer(Layout eLayout)
	{
		MaterialWidget aLayoutWidget;

		switch (eLayout)
		{
			case HEADER:
				aLayoutWidget = new MaterialCollapsibleHeader();
				break;

			case CONTENT:
				aLayoutWidget = new MaterialCollapsibleBody();
				break;

			default:
				aLayoutWidget = null;
		}

		return aLayoutWidget;
	}

	/***************************************
	 * Creates the global content layout widgets.
	 *
	 * @param  eLayout The content layout type
	 *
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createGlobalContentContainer(Layout eLayout)
	{
		MaterialWidget aLayoutWidget;

		switch (eLayout)
		{
			case HEADER:
				aLayoutWidget = new MaterialHeader();
				break;

			case CONTENT:
				aLayoutWidget = new MaterialContainer();
				break;

			case FOOTER:
				aLayoutWidget = new MaterialFooter();
				break;

			default:
				aLayoutWidget = null;
		}

		return aLayoutWidget;
	}

	/***************************************
	 * Returns the content area a certain widget represents.
	 *
	 * @param  rWidget
	 *
	 * @return The content area for this widget
	 */
	private ContentArea getContentArea(Widget rWidget)
	{
		ContentArea eContentArea;

		if (rWidget instanceof MaterialCollapsibleItem)
		{
			eContentArea = ContentArea.COLLAPSIBLE;
		}
		else if (rWidget instanceof MaterialCard)
		{
			eContentArea = ContentArea.CARD;
		}
		else
		{
			eContentArea = ContentArea.GLOBAL;
		}

		return eContentArea;
	}
}
