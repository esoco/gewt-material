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
/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package de.esoco.ewt.impl.gwt.material.widget;

import gwt.material.design.client.base.HasSelectables;
import gwt.material.design.client.base.HasSideNavHandlers;
import gwt.material.design.client.base.HasType;
import gwt.material.design.client.base.HasWaves;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.helper.DOMHelper;
import gwt.material.design.client.base.mixin.CssTypeMixin;
import gwt.material.design.client.constants.CssName;
import gwt.material.design.client.constants.Edge;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.constants.ShowOn;
import gwt.material.design.client.constants.SideNavType;
import gwt.material.design.client.events.ClearActiveEvent;
import gwt.material.design.client.events.SideNavClosedEvent;
import gwt.material.design.client.events.SideNavClosedEvent.SideNavClosedHandler;
import gwt.material.design.client.events.SideNavClosingEvent;
import gwt.material.design.client.events.SideNavClosingEvent.SideNavClosingHandler;
import gwt.material.design.client.events.SideNavOpenedEvent;
import gwt.material.design.client.events.SideNavOpenedEvent.SideNavOpenedHandler;
import gwt.material.design.client.events.SideNavOpeningEvent;
import gwt.material.design.client.events.SideNavOpeningEvent.SideNavOpeningHandler;
import gwt.material.design.client.js.JsMaterialElement;
import gwt.material.design.client.js.JsSideNavOptions;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.html.ListItem;
import gwt.material.design.jquery.client.api.JQuery;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import static gwt.material.design.client.js.JsMaterialElement.$;

//@formatter:off


/********************************************************************
 * SideNav is a material component that gives you a lists of menus and other
 * navigation components.
 *
 * <h3>UiBinder Usage:</h3>
 *
 * <pre>
   {@code
 * <m:MaterialSideNav ui:field="sideNav" width="280" m:id="mysidebar"  type="OPEN" closeOnClick="false">
 *     <m:MaterialLink href="#about" iconPosition="LEFT" iconType="OUTLINE" text="About" textColor="BLUE"  />
 *     <m:MaterialLink href="#gettingStarted" iconPosition="LEFT" iconType="DOWNLOAD" text="Getting Started" textColor="BLUE"  >
 * </m:MaterialSideNav>
 * }
 * </pre>
 *
 * @author kevzlou7979
 * @author Ben Dol
 * @see    <a
 *         href="http://gwtmaterialdesign.github.io/gwt-material-demo/#sidenavs">
 *         Material SideNav</a>
 */
//@formatter:on
@SuppressWarnings("boxing")
public class GewtMaterialSideNav extends MaterialWidget
	implements HasType<SideNavType>, HasSelectables, HasSideNavHandlers
{
	//~ Instance fields --------------------------------------------------------

	private int     width			    = 240;
	private Edge    edge			    = Edge.LEFT;
	private boolean closeOnClick	    = false;
	private boolean alwaysShowActivator = true;
	private boolean allowBodyScroll     = false;
	private boolean open;
	private Boolean showOnAttach;

	private Element activator;

	private final CssTypeMixin<SideNavType, GewtMaterialSideNav> typeMixin					  =
		new CssTypeMixin<>(this);
	private HandlerRegistration									 overlayOpeningHandler;
	private HandlerRegistration									 pushWithHeaderOpeningHandler;
	private HandlerRegistration									 pushWithHeaderClosingHandler;
	private HandlerRegistration									 cardOpenedHandler;
	private HandlerRegistration									 cardClosedHandler;
	private HandlerRegistration									 cardOpeningHandler;
	private HandlerRegistration									 cardClosingHandler;

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Container for App Toolbar and App Sidebar , contains Material Links,
	 * Icons or any other material components.
	 */
	public GewtMaterialSideNav()
	{
		super(Document.get().createULElement(), CssName.SIDE_NAV);

		typeMixin.setType(SideNavType.FIXED);
	}

	/***************************************
	 * Creates a list and adds the given widgets.
	 *
	 * @param widgets DOCUMENT ME!
	 */
	public GewtMaterialSideNav(final Widget... widgets)
	{
		this();

		for (final Widget w : widgets)
		{
			add(w);
		}
	}

	/***************************************
	 * Creates a new instance.
	 *
	 * @param type DOCUMENT ME!
	 */
	@UiConstructor
	public GewtMaterialSideNav(SideNavType type)
	{
		this();
		setType(type);
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * @see MaterialWidget#add(com.google.gwt.user.client.ui.IsWidget)
	 */
	@Override
	public void add(Widget child)
	{
		super.add(wrap(child));
	}

	/***************************************
	 * @see HasSideNavHandlers#addClosedHandler(SideNavClosedHandler)
	 */
	@Override
	public HandlerRegistration addClosedHandler(SideNavClosedHandler handler)
	{
		return addHandler(handler, SideNavClosedEvent.TYPE);
	}

	/***************************************
	 * @see HasSideNavHandlers#addClosingHandler(SideNavClosingHandler)
	 */
	@Override
	public HandlerRegistration addClosingHandler(SideNavClosingHandler handler)
	{
		return addHandler(handler, SideNavClosingEvent.TYPE);
	}

	/***************************************
	 * @see HasSideNavHandlers#addOpenedHandler(SideNavOpenedHandler)
	 */
	@Override
	public HandlerRegistration addOpenedHandler(SideNavOpenedHandler handler)
	{
		return addHandler(handler, SideNavOpenedEvent.TYPE);
	}

	/***************************************
	 * @see HasSideNavHandlers#addOpeningHandler(SideNavOpeningHandler)
	 */
	@Override
	public HandlerRegistration addOpeningHandler(SideNavOpeningHandler handler)
	{
		return addHandler(handler, SideNavOpeningEvent.TYPE);
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasSelectables#clearActive()
	 */
	@Override
	public void clearActive()
	{
		clearActiveClass(this);
		ClearActiveEvent.fire(this);
	}

	/***************************************
	 * Returns the edge.
	 *
	 * @return The edge
	 */
	public Edge getEdge()
	{
		return edge;
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasType#getType()
	 */
	@Override
	public SideNavType getType()
	{
		return typeMixin.getType();
	}

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#getWidth()
	 */
	@Override
	public int getWidth()
	{
		return width;
	}

	/***************************************
	 * Hide the sidenav using the activator element
	 */
	public void hide()
	{
		$(activator).sideNav("hide");
	}

	/***************************************
	 * Hide the overlay menu.
	 */
	public void hideOverlay()
	{
		$("#sidenav-overlay").remove();
	}

	/***************************************
	 * Will the body have scroll capability while the menu is open.
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isAllowBodyScroll()
	{
		return allowBodyScroll;
	}

	/***************************************
	 * Will the activator always be shown.
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isAlwaysShowActivator()
	{
		return alwaysShowActivator;
	}

	/***************************************
	 * Returns the close on click.
	 *
	 * @return The close on click
	 */
	public boolean isCloseOnClick()
	{
		return closeOnClick;
	}

	/***************************************
	 * Returns the open.
	 *
	 * @return The open
	 */
	public boolean isOpen()
	{
		return open;
	}

	/***************************************
	 * Will the menu forcefully show on attachment.
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isShowOnAttach()
	{
		return showOnAttach != null && showOnAttach;
	}

	/***************************************
	 * Reinitialize the side nav configurations when changing properties.
	 */
	@Override
	public void reinitialize()
	{
		activator = null;
		initialize(false);
	}

	/***************************************
	 * Allow the body to maintain its scroll capability while the menu is
	 * visible.
	 *
	 * @param allowBodyScroll DOCUMENT ME!
	 */
	public void setAllowBodyScroll(boolean allowBodyScroll)
	{
		this.allowBodyScroll = allowBodyScroll;
	}

	/***************************************
	 * Disable the hiding of your activator element.
	 *
	 * @param alwaysShowActivator DOCUMENT ME!
	 */
	public void setAlwaysShowActivator(boolean alwaysShowActivator)
	{
		this.alwaysShowActivator = alwaysShowActivator;
	}

	/***************************************
	 * Close the side nav menu when an \<a\> tag is clicked from inside it. Note
	 * that if you want this to work you must wrap your item within a {@link
	 * MaterialLink}.
	 *
	 * @param closeOnClick DOCUMENT ME!
	 */
	public void setCloseOnClick(boolean closeOnClick)
	{
		this.closeOnClick = closeOnClick;
	}

	/***************************************
	 * Set which edge of the window the menu should attach to.
	 *
	 * @param edge DOCUMENT ME!
	 */
	public void setEdge(Edge edge)
	{
		this.edge = edge;
	}

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled)
	{
		getEnabledMixin().setEnabled(this, enabled);
	}

	/***************************************
	 * Show the menu upon attachment.<br>
	 * Note that you shouldn't apply this setting if you want your side nav to
	 * appear static. otherwise when set to <code>true</code> will slide in from
	 * the left.
	 *
	 * @param showOnAttach DOCUMENT ME!
	 */
	public void setShowOnAttach(boolean showOnAttach)
	{
		this.showOnAttach = showOnAttach;
	}

	/***************************************
	 * Define the menu's type specification.
	 *
	 * @param type DOCUMENT ME!
	 */
	@Override
	public void setType(SideNavType type)
	{
		typeMixin.setType(type);
	}

	/***************************************
	 * @see com.google.gwt.user.client.ui.UIObject#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width)
	{
		setWidth(Integer.parseInt(width));
	}

	/***************************************
	 * Set the menu's width in pixels.
	 *
	 * @param width DOCUMENT ME!
	 */
	public void setWidth(int width)
	{
		this.width = width;
		getElement().getStyle().setWidth(width, Unit.PX);
	}

	/***************************************
	 * Show the sidenav using the activator element
	 */
	public void show()
	{
		$(activator).sideNav("show");
	}

	/***************************************
	 * DOCUMENT ME!
	 *
	 * @param  child DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Widget wrap(Widget child)
	{
		if (child instanceof MaterialImage)
		{
			child.getElement().getStyle()
				 .setProperty("border", "1px solid #e9e9e9");
			child.getElement().getStyle().setProperty("textAlign", "center");
		}

		// Check whether the widget is not selectable by default
		boolean isNotSelectable = false;

		if (child instanceof MaterialWidget)
		{
			MaterialWidget widget = (MaterialWidget) child;

			if (widget.getInitialClasses() != null)
			{
				if (widget.getInitialClasses().length > 0)
				{
					String initialClass = widget.getInitialClasses()[0];

					if (initialClass.contains(CssName.SIDE_PROFILE) ||
						initialClass.contains(CssName.COLLAPSIBLE))
					{
						isNotSelectable = true;
					}
				}
			}
		}

		if (!(child instanceof ListItem))
		{
			// Direct list item not collapsible
			final ListItem listItem = new ListItem();

			if (child instanceof MaterialCollapsible)
			{
				listItem.getElement().getStyle()
						.setBackgroundColor("transparent");
			}

			if (child instanceof HasWaves)
			{
				listItem.setWaves(((HasWaves) child).getWaves());
				((HasWaves) child).setWaves(null);
			}

			listItem.add(child);

			child = listItem;
		}

		// Collapsible and Side Porfile should not be selectable
		final Widget finalChild = child;

		if (!isNotSelectable)
		{
			// Active click handler
			finalChild.addDomHandler(event ->
					 				{
					 					clearActive();
					 					finalChild.addStyleName(CssName.ACTIVE);
									 },
									 ClickEvent.getType());
		}

		child.getElement().getStyle().setDisplay(Style.Display.BLOCK);

		return child;
	}

	/***************************************
	 * Applies a card that contains a shadow and this type is good for few
	 * sidenav link items
	 */
	protected void applyCardType()
	{
		$("main").css("transition", "0.2s all");

		if (cardOpeningHandler == null)
		{
			cardOpeningHandler =
				addOpeningHandler(event -> pushElement(getMain(), width + 20));
		}

		if (cardOpenedHandler == null)
		{
			cardOpenedHandler = addOpenedHandler(event -> setLeft(0));
		}

		if (cardClosingHandler == null)
		{
			cardClosingHandler =
				addClosingHandler(event -> pushElement(getMain(), 0));
		}

		if (cardClosedHandler == null)
		{
			cardClosedHandler =
				addClosedHandler(event -> setLeft(-(width + 20)));
		}
	}

	/***************************************
	 * Provides a Fixed type sidenav which by default on desktop - activator
	 * will notbe visible but you can configure it by setting the property
	 * setAlwaysShowActivator() to true
	 */
	protected void applyFixedType()
	{
		$(JQuery.window()).off("resize")
						  .resize((e, param1) ->
					  			{
					  				if (gwt.material.design.client.js.Window
					  					.matchMedia("all and (min-width: 992px)"))
					  				{
					  					Scheduler.get()
					  					.scheduleDeferred(() ->
					  									  show());
					  				}

					  				return true;
								  });

		Scheduler.get()
				 .scheduleDeferred(() ->
					   			{
					   				pushElement(getHeader(), this.width);
					   				pushElement(getMain(), this.width);
					   				pushElement(getFooter(), this.width);
								   });
	}

	/***************************************
	 * Provides an overlay sidenav just like when opening sidenav on mobile /
	 * tablet
	 */
	protected void applyOverlayType()
	{
		setShowOnAttach(false);

		if (overlayOpeningHandler == null)
		{
			overlayOpeningHandler =
				addOpeningHandler(event ->
			  					{
			  						Scheduler.get()
			  						.scheduleDeferred(() ->
			  										  $("#sidenav-overlay")
			  										  .css("visibility",
			  											   "visible"));
								  });
		}

		Scheduler.get()
				 .scheduleDeferred(() ->
					   			{
					   				pushElement(getHeader(), 0);
					   				pushElement(getMain(), 0);
								   });
	}

	/***************************************
	 * Provides an overlay sidenav that will float on top of the content not the
	 * navbar without any grey overlay behind it.
	 */
	protected void applyOverlayWithHeaderType()
	{
		setShowOnAttach(false);
		$("main").css("transition", "0.2s all");

		if (showOnAttach != null && showOnAttach)
		{
			Scheduler.get()
					 .scheduleDeferred(() ->
					   				{
					   					pushElement(getHeader(), 0);
					   					pushElement(getMain(), 0);
									   });
		}
	}

	/***************************************
	 * Push the header, footer, and main to the right part when Close type is
	 * applied.
	 */
	protected void applyPushType()
	{
		$(JQuery.window()).off("resize")
						  .resize((e, param1) ->
					  			{
					  				if (!isAlwaysShowActivator() &&
					  					!isOpen() &&
					  					gwt.material.design.client.js.Window
					  					.matchMedia("all and (min-width: 992px)"))
					  				{
					  					show();
					  				}

					  				pushElements(open, this.width);

					  				return true;
								  });
	}

	/***************************************
	 * DOCUMENT ME!
	 */
	protected void applyPushWithHeaderType()
	{
		$("main").css("transition", "0.2s all");

		if (showOnAttach != null && showOnAttach)
		{
			Scheduler.get()
					 .scheduleDeferred(() ->
					   				{
					   					pushElement(getHeader(), 0);
					   					pushElement(getMain(), this.width);
									   });
		}

		if (pushWithHeaderOpeningHandler == null)
		{
			pushWithHeaderOpeningHandler =
				addOpeningHandler(event -> pushElement(getMain(), this.width));
		}

		if (pushWithHeaderClosingHandler == null)
		{
			pushWithHeaderClosingHandler =
				addClosingHandler(event -> pushElement(getMain(), 0));
		}
	}

	/***************************************
	 * DOCUMENT ME!
	 *
	 * @param elem     DOCUMENT ME!
	 * @param width    DOCUMENT ME!
	 * @param duration DOCUMENT ME!
	 */
	protected void applyTransition(Element elem, int width, int duration)
	{
		$(elem).css("transition", duration + "ms");
		$(elem).css("-moz-transition", duration + "ms");
		$(elem).css("-webkit-transition", duration + "ms");
		$(elem).css("margin-left", width + "px");
	}

	/***************************************
	 * Returns the footer.
	 *
	 * @return The footer
	 */
	protected Element getFooter()
	{
		return $("footer").asElement();
	}

	/***************************************
	 * Returns the header.
	 *
	 * @return The header
	 */
	protected Element getHeader()
	{
		return $("header").asElement();
	}

	/***************************************
	 * Returns the main.
	 *
	 * @return The main
	 */
	protected Element getMain()
	{
		return $("main").asElement();
	}

	/***************************************
	 * Returns the nav menu.
	 *
	 * @return The nav menu
	 */
	protected MaterialWidget getNavMenu()
	{
		Element navMenuElement =
			DOMHelper.getElementByAttribute("data-activates", getId());

		if (navMenuElement != null)
		{
			return new MaterialWidget(navMenuElement);
		}

		return null;
	}

	/***************************************
	 * DOCUMENT ME!
	 */
	@Override
	protected void initialize()
	{
		initialize(true);
	}

	/***************************************
	 * DOCUMENT ME!
	 *
	 * @param strict DOCUMENT ME!
	 */
	protected void initialize(boolean strict)
	{
		try
		{
			activator =
				DOMHelper.getElementByAttribute("data-activates", getId());
			getNavMenu().setShowOn(ShowOn.SHOW_ON_MED_DOWN);

			if (alwaysShowActivator && getType() != SideNavType.FIXED)
			{
				getNavMenu().setShowOn(ShowOn.SHOW_ON_LARGE);
			}
			else
			{
				getNavMenu().setHideOn(HideOn.HIDE_ON_LARGE);
			}

			getNavMenu().removeStyleName(CssName.NAVMENU_PERMANENT);
		}
		catch (Exception ex)
		{
			if (strict)
			{
				throw new IllegalArgumentException("Could not setup MaterialSideNav please ensure you have MaterialNavBar with an activator setup to match this widgets id.");
			}
		}

		SideNavType type = getType();

		processType(type);

		JsSideNavOptions options = new JsSideNavOptions();

		options.menuWidth    = width;
		options.edge		 = edge != null ? edge.getCssName() : null;
		options.closeOnClick = closeOnClick;

		JsMaterialElement element = $(activator);

		element.sideNav(options);

		element.off("side-nav-closing");
		element.on("side-nav-closing", e1 ->
	   			{
	   				onClosing();

	   				return true;
				   });

		element.off("side-nav-closed");
		element.on("side-nav-closed", e1 ->
	   			{
	   				onClosed();

	   				return true;
				   });

		element.off("side-nav-opening");
		element.on("side-nav-opening", e1 ->
	   			{
	   				onOpening();

	   				return true;
				   });

		element.off("side-nav-opened");
		element.on("side-nav-opened", e1 ->
	   			{
	   				onOpened();

	   				return true;
				   });
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("deprecation")
	protected void insert(Widget							 child,
						  com.google.gwt.user.client.Element container,
						  int								 beforeIndex,
						  boolean							 domInsert)
	{
		super.insert(wrap(child), container, beforeIndex, domInsert);
	}

	/***************************************
	 * Returns the small.
	 *
	 * @return The small
	 */
	protected boolean isSmall()
	{
		return !gwt.material.design.client.js.Window.matchMedia("all and (max-width: 992px)");
	}

	/***************************************
	 * DOCUMENT ME!
	 */
	protected void onClosed()
	{
		SideNavClosedEvent.fire(this);
	}

	/***************************************
	 * DOCUMENT ME!
	 */
	protected void onClosing()
	{
		open = false;

		if (getType().equals(SideNavType.PUSH))
		{
			pushElements(false, this.width);
		}

		SideNavClosingEvent.fire(this);
	}

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#onLoad()
	 */
	@Override
	protected void onLoad()
	{
		super.onLoad();

		// Initialize the side nav
		initialize();

		if (showOnAttach != null)
		{
			// Ensure the side nav starts closed
			$(activator).trigger("menu-in", null);

			if (showOnAttach)
			{
				Scheduler.get()
						 .scheduleDeferred(() ->
					   					{
					   						// We are ignoring cases with mobile
					   						if (Window.getClientWidth() >
					   							960)
					   						{
					   							show();
					   						}
										   });
			}
		}
		else
		{
			if (!getType().equals(SideNavType.CARD))
			{
				setLeft(0);
			}

			$(activator).trigger("menu-out", null);
		}
	}

	/***************************************
	 * DOCUMENT ME!
	 */
	protected void onOpened()
	{
		if (allowBodyScroll)
		{
			RootPanel.getBodyElement().getStyle().clearOverflow();
		}

		SideNavOpenedEvent.fire(this);
	}

	/***************************************
	 * DOCUMENT ME!
	 */
	protected void onOpening()
	{
		open = true;

		if (getType().equals(SideNavType.PUSH))
		{
			pushElements(true, this.width);
		}

		SideNavOpeningEvent.fire(this);
	}

	/***************************************
	 * DOCUMENT ME!
	 *
	 * @param toggle   DOCUMENT ME!
	 * @param width    DOCUMENT ME!
	 * @param duration DOCUMENT ME!
	 */
	protected void onPush(boolean toggle, int width, int duration)
	{
//		SideNavPushEvent.fire(this,
//							  getElement(),
//							  activator,
//							  toggle,
//							  width,
//							  duration);
	}

	/***************************************
	 * @see com.google.gwt.user.client.ui.Widget#onUnload()
	 */
	@Override
	protected void onUnload()
	{
		super.onUnload();

		$("#sidenav-overlay").remove();
		activator = null;
	}

	/***************************************
	 * DOCUMENT ME!
	 *
	 * @param type DOCUMENT ME!
	 */
	protected void processType(SideNavType type)
	{
		if (activator != null && type != null)
		{
			addStyleName(type.getCssName());

			switch (type)
			{
				case FIXED:
					applyFixedType();
					break;

				case DRAWER:
					applyOverlayType();
					break;

				case DRAWER_WITH_HEADER:
					applyOverlayWithHeaderType();
					break;

				case PUSH:
					applyPushType();
					break;

				case PUSH_WITH_HEADER:
					applyPushWithHeaderType();
					break;

				case CARD:
					applyCardType();
					break;

				case MINI:
					setWidth(64);
					break;

				default:
					throw new UnsupportedOperationException("Unkown type " +
															type);
			}
		}
	}

	/***************************************
	 * DOCUMENT ME!
	 *
	 * @param element     DOCUMENT ME!
	 * @param paddingLeft DOCUMENT ME!
	 */
	protected void pushElement(Element element, int paddingLeft)
	{
		$(element).css("paddingLeft", paddingLeft + "px");
	}

	/***************************************
	 * DOCUMENT ME!
	 *
	 * @param toggle DOCUMENT ME!
	 * @param width  DOCUMENT ME!
	 */
	protected void pushElements(boolean toggle, int width)
	{
		int w   = 0;
		int dur = 200;

		if (!gwt.material.design.client.js.Window.matchMedia("all and (max-width: 992px)"))
		{
			if (toggle)
			{
				w   = width;
				dur = 300;
			}

			applyTransition($("header").asElement(), w, dur);
			applyTransition($("main").asElement(), w, dur);
			applyTransition($("footer").asElement(), w, dur);
		}

		onPush(toggle, w, dur);
	}
}