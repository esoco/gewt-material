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

	private LayoutType layout;

	/**
	 * Creates a new instance.
	 *
	 * @param layout The layout of the content
	 */
	public MaterialContentLayout(LayoutType layout) {
		this.layout = layout;
	}

	@Override
	protected MaterialWidget creatMaterialLayoutContainer(Container container,
		StyleData containerStyle) {
		Widget parentWidget = container.getParent().getWidget();
		MaterialWidget layoutWidget = null;
		ContentArea contentArea;

		contentArea = getContentArea(parentWidget);

		switch (contentArea) {
			case CARD:
				layoutWidget = createCardContentContainer(layout);
				break;

			case COLLAPSIBLE:
				layoutWidget = createCollapsibleContentContainer(layout);
				break;

			case COLLECTION:
				layoutWidget = createCollectionContentContainer(layout);
				break;

			case DIALOG:
				layoutWidget = createModalContentContainer(layout);
				break;

			case MENU:
				layoutWidget = createMenuContentContainer(layout);
				break;

			case GLOBAL:
				layoutWidget = createGlobalContentContainer(layout);
				break;
		}

		if (layoutWidget == null) {
			throw new IllegalArgumentException(
				"Unsupported content layout " + layout + " for " +
					parentWidget.getClass().getSimpleName());
		}

		return layoutWidget;
	}

	/**
	 * Creates the content layout widgets for a {@link MaterialCard} container.
	 *
	 * @param layout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createCardContentContainer(LayoutType layout) {
		MaterialWidget layoutWidget;

		switch (layout) {
			case CONTENT:
				layoutWidget = new MaterialCardContent();
				break;

			case SECONDARY_CONTENT:
				layoutWidget = new MaterialCardReveal();
				break;

			case FOOTER:
				layoutWidget = new MaterialCardAction();
				break;

			default:
				layoutWidget = null;
		}

		return layoutWidget;
	}

	/**
	 * Creates the content layout widgets for a {@link MaterialCollapsible}
	 * container.
	 *
	 * @param layout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createCollapsibleContentContainer(LayoutType layout) {
		MaterialWidget layoutWidget;

		switch (layout) {
			case HEADER:
				layoutWidget = new GewtMaterialCollapsibleHeader();
				break;

			case CONTENT:
				layoutWidget = new MaterialCollapsibleBody();
				break;

			default:
				layoutWidget = null;
		}

		return layoutWidget;
	}

	/**
	 * Creates the content layout widgets for a {@link MaterialCollapsible}
	 * container.
	 *
	 * @param layout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createCollectionContentContainer(LayoutType layout) {
		MaterialWidget layoutWidget;

		switch (layout) {
			case SECONDARY_CONTENT:
				layoutWidget = new MaterialCollectionSecondary();
				break;

			default:
				layoutWidget = null;
		}

		return layoutWidget;
	}

	/**
	 * Creates the global content layout widgets.
	 *
	 * @param layout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createGlobalContentContainer(LayoutType layout) {
		MaterialWidget layoutWidget;

		switch (layout) {
			case HEADER:
				layoutWidget = new MaterialHeader();
				break;

			case CONTENT:
				layoutWidget = new MaterialContainer();
				break;

			case FOOTER:
				layoutWidget = new MaterialFooter();
				break;

			default:
				layoutWidget = null;
		}

		return layoutWidget;
	}

	/**
	 * Creates the content layout widgets for a {@link MaterialHeader}
	 * container.
	 *
	 * @param layout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createMenuContentContainer(LayoutType layout) {
		MaterialWidget layoutWidget;

		switch (layout) {
			case SECONDARY_CONTENT:
				layoutWidget = new MaterialNavSection();
				layoutWidget.setHideOn(HideOn.NONE);
				break;

			default:
				layoutWidget = null;
		}

		return layoutWidget;
	}

	/**
	 * Creates the content layout widgets for a {@link MaterialModal}
	 * container.
	 *
	 * @param layout The content layout type
	 * @return A new widget container or NULL if no match was available
	 */
	MaterialWidget createModalContentContainer(LayoutType layout) {
		MaterialWidget layoutWidget;

		switch (layout) {
			case CONTENT:
				layoutWidget = new MaterialDialogContent();
				break;

			case FOOTER:
				layoutWidget = new MaterialDialogFooter();
				break;

			default:
				layoutWidget = null;
		}

		return layoutWidget;
	}

	/**
	 * Returns the content area a certain widget represents.
	 *
	 * @return The content area for this widget
	 */
	private ContentArea getContentArea(Widget parent) {
		ContentArea contentArea;

		if (parent instanceof MaterialCollapsibleItem) {
			contentArea = ContentArea.COLLAPSIBLE;
		} else if (parent instanceof MaterialCollectionItem) {
			contentArea = ContentArea.COLLECTION;
		} else if (parent instanceof MaterialCard) {
			contentArea = ContentArea.CARD;
		} else if (parent instanceof MaterialDialog) {
			contentArea = ContentArea.DIALOG;
		} else if (parent instanceof MaterialNavBar ||
			parent instanceof AbstractSideNav) {
			contentArea = ContentArea.MENU;
		} else {
			contentArea = ContentArea.GLOBAL;
		}

		return contentArea;
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
		 * @param widgets The header widgets
		 */
		public GewtMaterialCollapsibleHeader(Widget... widgets) {
			super(widgets);
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
		public void setActive(boolean active) {
			MaterialCollapsible parent = (MaterialCollapsible) getParent();

			parent.setActive(parent.getWidgetIndex(this) + 1);
		}
	}
}
