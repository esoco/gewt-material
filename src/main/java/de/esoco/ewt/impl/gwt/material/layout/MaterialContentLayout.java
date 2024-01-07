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
package de.esoco.ewt.impl.gwt.material.layout;

import gwt.material.design.client.base.AbstractSideNav;
import gwt.material.design.client.base.HasActive;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardReveal;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialDialogContent;
import gwt.material.design.client.ui.MaterialDialogFooter;
import gwt.material.design.client.ui.MaterialFooter;
import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavSection;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.ActiveState;
import de.esoco.lib.property.LayoutType;

import com.google.gwt.user.client.ui.Widget;

/**
 * GWT Material implementation for content-specific layouts.
 *
 * @author eso
 */
public class MaterialContentLayout extends AbstractMaterialLayout {

	/**
	 * Internal enumeration of the different content areas to consider.
	 */
	private enum ContentArea {
		GLOBAL, CARD, COLLAPSIBLE, COLLECTION, MENU, DIALOG
	}

	private LayoutType eLayout;

	/**
	 * Creates a new instance.
	 *
	 * @param eLayout The layout of the content
	 */
	public MaterialContentLayout(LayoutType eLayout) {
		this.eLayout = eLayout;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MaterialWidget creatMaterialLayoutContainer(Container rContainer,
		StyleData rContainerStyle) {
		Widget rParentWidget = rContainer.getParent().getWidget();
		MaterialWidget aLayoutWidget = null;
		ContentArea eContentArea;

		eContentArea = getContentArea(rParentWidget);

		switch (eContentArea) {
			case CARD:
				aLayoutWidget = createCardContentContainer(eLayout);
				break;

			case COLLAPSIBLE:
				aLayoutWidget = createCollapsibleContentContainer(eLayout);
				break;

			case COLLECTION:
				aLayoutWidget = createCollectionContentContainer(eLayout);
				break;

			case DIALOG:
				aLayoutWidget = createModalContentContainer(eLayout);
				break;

			case MENU:
				aLayoutWidget = createMenuContentContainer(eLayout);
				break;

			case GLOBAL:
				aLayoutWidget = createGlobalContentContainer(eLayout);
				break;
		}

		if (aLayoutWidget == null) {
			throw new IllegalArgumentException(
				"Unsupported content layout " + eLayout + " for " +
					rParentWidget.getClass().getSimpleName());
		}

		return aLayoutWidget;
	}

	/**
	 * Creates the content layout widgets for a {@link MaterialCard} container.
	 *
	 * @param eLayout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createCardContentContainer(LayoutType eLayout) {
		MaterialWidget aLayoutWidget;

		switch (eLayout) {
			case CONTENT:
				aLayoutWidget = new MaterialCardContent();
				break;

			case SECONDARY_CONTENT:
				aLayoutWidget = new MaterialCardReveal();
				break;

			case FOOTER:
				aLayoutWidget = new MaterialCardAction();
				break;

			default:
				aLayoutWidget = null;
		}

		return aLayoutWidget;
	}

	/**
	 * Creates the content layout widgets for a {@link MaterialCollapsible}
	 * container.
	 *
	 * @param eLayout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createCollapsibleContentContainer(LayoutType eLayout) {
		MaterialWidget aLayoutWidget;

		switch (eLayout) {
			case HEADER:
				aLayoutWidget = new GewtMaterialCollapsibleHeader();
				break;

			case CONTENT:
				aLayoutWidget = new MaterialCollapsibleBody();
				break;

			default:
				aLayoutWidget = null;
		}

		return aLayoutWidget;
	}

	/**
	 * Creates the content layout widgets for a {@link MaterialCollapsible}
	 * container.
	 *
	 * @param eLayout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createCollectionContentContainer(LayoutType eLayout) {
		MaterialWidget aLayoutWidget;

		switch (eLayout) {
			case SECONDARY_CONTENT:
				aLayoutWidget = new MaterialCollectionSecondary();
				break;

			default:
				aLayoutWidget = null;
		}

		return aLayoutWidget;
	}

	/**
	 * Creates the global content layout widgets.
	 *
	 * @param eLayout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createGlobalContentContainer(LayoutType eLayout) {
		MaterialWidget aLayoutWidget;

		switch (eLayout) {
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

	/**
	 * Creates the content layout widgets for a {@link MaterialHeader}
	 * container.
	 *
	 * @param eLayout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createMenuContentContainer(LayoutType eLayout) {
		MaterialWidget aLayoutWidget;

		switch (eLayout) {
			case SECONDARY_CONTENT:
				aLayoutWidget = new MaterialNavSection();
				aLayoutWidget.setHideOn(HideOn.NONE);
				break;

			default:
				aLayoutWidget = null;
		}

		return aLayoutWidget;
	}

	/**
	 * Creates the content layout widgets for a {@link MaterialModal}
	 * container.
	 *
	 * @param eLayout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createModalContentContainer(LayoutType eLayout) {
		MaterialWidget aLayoutWidget;

		switch (eLayout) {
			case CONTENT:
				aLayoutWidget = new MaterialDialogContent();
				break;

			case FOOTER:
				aLayoutWidget = new MaterialDialogFooter();
				break;

			default:
				aLayoutWidget = null;
		}

		return aLayoutWidget;
	}

	/**
	 * Returns the content area a certain widget represents.
	 *
	 * @return The content area for this widget
	 */
	private ContentArea getContentArea(Widget rParent) {
		ContentArea eContentArea;

		if (rParent instanceof MaterialCollapsibleItem) {
			eContentArea = ContentArea.COLLAPSIBLE;
		} else if (rParent instanceof MaterialCollectionItem) {
			eContentArea = ContentArea.COLLECTION;
		} else if (rParent instanceof MaterialCard) {
			eContentArea = ContentArea.CARD;
		} else if (rParent instanceof MaterialDialog) {
			eContentArea = ContentArea.DIALOG;
		} else if (rParent instanceof MaterialNavBar ||
			rParent instanceof AbstractSideNav) {
			eContentArea = ContentArea.MENU;
		} else {
			eContentArea = ContentArea.GLOBAL;
		}

		return eContentArea;
	}

	/**
	 * Subclassed to implement the {@link HasActive} interface.
	 *
	 * @author eso
	 */
	public static class GewtMaterialCollapsibleHeader
		extends MaterialCollapsibleHeader implements ActiveState {

		/**
		 * Creates a new instance.
		 */
		public GewtMaterialCollapsibleHeader() {
		}

		/**
		 * Creates a new instance.
		 *
		 * @param rWidgets The header widgets
		 */
		public GewtMaterialCollapsibleHeader(Widget... rWidgets) {
			super(rWidgets);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isActive() {
			return getStyleName().contains("active");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setActive(boolean bActive) {
			MaterialCollapsible rParent = (MaterialCollapsible) getParent();

			rParent.setActive(rParent.getWidgetIndex(this) + 1);
		}
	}
}
