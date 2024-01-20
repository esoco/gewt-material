//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2019 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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

import gwt.material.design.client.data.AbstractDataView;
import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.DataView;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.SortContext;
import gwt.material.design.client.data.SortDir;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.data.infinite.HasLoader;
import gwt.material.design.client.data.infinite.InfiniteDataView;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.Column;
import gwt.material.design.jquery.client.api.JQuery;

import de.esoco.ewt.UserInterfaceContext;
import de.esoco.ewt.component.TableControl.IsTableControlWidget;
import de.esoco.ewt.event.EventType;
import de.esoco.ewt.impl.gwt.GewtEventDispatcher;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.model.Callback;
import de.esoco.lib.model.ColumnDefinition;
import de.esoco.lib.model.DataModel;
import de.esoco.lib.model.RemoteDataModel;
import de.esoco.lib.model.SortableDataModel;
import de.esoco.lib.property.ContentType;
import de.esoco.lib.property.Indexed;
import de.esoco.lib.property.SortDirection;
import de.esoco.lib.property.StyleProperties;
import de.esoco.lib.property.TableStyle;
import de.esoco.lib.property.TitleAttribute;

import java.sql.Time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.Range;

import static de.esoco.lib.property.ContentProperties.CONTENT_TYPE;
import static de.esoco.lib.property.StateProperties.SORT_DIRECTION;
import static de.esoco.lib.property.StyleProperties.TABLE_STYLE;

/**
 * GWT Material implementation of {@link IsTableControlWidget}.
 *
 * @author eso
 */
public class GewtMaterialTable extends Composite
	implements IsTableControlWidget, TitleAttribute {

	private UserInterfaceContext context;

	private GewtMaterialDataTable<DataModel<?>> materialTable;

	private DataModel<DataModel<?>> dataModel;

	private DataModel<ColumnDefinition> columns;

	/**
	 * Creates a new instance.
	 *
	 * @param context The user interface context
	 * @param style   The table style
	 */
	public GewtMaterialTable(UserInterfaceContext context, StyleData style) {
		this.context = context;

		switch (style.getProperty(TABLE_STYLE, TableStyle.FIXED)) {
			case AUTO_LOAD:
				materialTable = new GewtMaterialInfiniteDataTable<>();
				break;

			case PAGED:
				materialTable = new GewtMaterialPagingDataTable<>();
				break;

			default:
				materialTable = new GewtMaterialDataTable<>();
				materialTable.setVisibleRange(new Range(0, 100));
		}

		initWidget(materialTable);
	}

	/**
	 * Returns the date time format.
	 *
	 * @param columnDef datatype The date time format
	 * @return The date time format
	 */
	public static DateTimeFormat getDateTimeFormat(ColumnDefinition columnDef) {
		String datatype = columnDef.getDatatype();
		DateTimeFormat format =
			DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT);

		if (columnDef.getProperty(CONTENT_TYPE, null) !=
			ContentType.DATE_TIME) {
			if (Date.class.getName().endsWith(datatype)) {
				format = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
			} else if (Time.class.getName().endsWith(datatype)) {
				format = DateTimeFormat.getFormat(PredefinedFormat.TIME_SHORT);
			}
		}

		return format;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public DataModel<ColumnDefinition> getColumns() {
		return columns;
	}

	@Override
	public DataModel<?> getData() {
		return dataModel;
	}

	@Override
	public DataModel<?> getSelection() {
		return materialTable.getSelection();
	}

	@Override
	public int getSelectionIndex() {
		DataModel<?> selection = getSelection();

		return selection instanceof Indexed ?
		       ((Indexed) selection).getIndex() :
		       -1;
	}

	@Override
	public int getTabIndex() {
		return materialTable.getTabIndex();
	}

	@Override
	public String getTableTitle() {
		return materialTable.getTableTitle().getText();
	}

	@Override
	public boolean isEnabled() {
		return materialTable.isEnabled();
	}

	@Override
	public void repaint() {
		materialTable.update();
	}

	@Override
	public void setAccessKey(char key) {
		materialTable.setAccessKey(key);
	}

	@Override
	public void setColumns(DataModel<ColumnDefinition> newColumns) {
		if (!newColumns.equals(columns)) {
			columns = newColumns;

			int column = 0;

			materialTable.removeColumns();

			for (ColumnDefinition columnDef : newColumns) {
				Column<DataModel<?>, ?> materialColumn;

				switch (columnDef.getDatatype()) {
					case "Date":
						materialColumn = new DateColumn(columnDef, column);
						break;

					default:
						materialColumn = new TextColumn(columnDef, column);
				}

				materialTable.addColumn(materialColumn);
				column++;
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setData(DataModel<? extends DataModel<?>> newData) {
		if (newData != dataModel) {
			dataModel = (DataModel<DataModel<?>>) newData;

			DataModelDataSource dataSource =
				new DataModelDataSource(dataModel);

			materialTable.updateDataSource(dataSource);
			materialTable.update();
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		materialTable.setEnabled(enabled);
	}

	@Override
	public void setEventDispatcher(GewtEventDispatcher eventDispatcher) {
		if (materialTable.getSelectionType() == SelectionType.NONE) {
			materialTable.addRowShortPressHandler(
				e -> eventDispatcher.dispatchEvent(EventType.SELECTION));
		} else {
			materialTable.addRowSelectHandler(
				e -> eventDispatcher.dispatchEvent(EventType.SELECTION));
		}

		materialTable.addRowDoubleClickHandler(
			e -> eventDispatcher.dispatchEvent(EventType.ACTION));
	}

	@Override
	public void setFocus(boolean focused) {
		materialTable.setFocus(focused);
	}

	@Override
	public void setSelection(int index) {
		setSelection(index, false);
	}

	/**
	 * {@inheritDoc} boolean)
	 */
	@Override
	public void setSelection(int index, boolean fireEvent) {
		// currently not supported by material table
	}

	@Override
	public void setTabIndex(int index) {
		materialTable.setTabIndex(index);
	}

	@Override
	public void setTableTitle(String tableTitle) {
		materialTable
			.getScaffolding()
			.getTopPanel()
			.setVisible(tableTitle != null);

		if (tableTitle != null) {
			materialTable.getTableTitle().setText(tableTitle);
		}
	}

	@Override
	public void setVisibleRowCount(int count) {
		materialTable.setVisibleRange(0, count);
	}

	@Override
	protected void initWidget(Widget widget) {
		super.initWidget(widget);

		setTableTitle(null);

		materialTable.setSelectionType(SelectionType.SINGLE);
		materialTable.addColumnSortHandler(
			e -> handleColumnSort(e.getSortContext()));
	}

	/**
	 * Handles the sorting by a certain column.
	 *
	 * @param sortContext The material sort context
	 */
	private void handleColumnSort(SortContext<DataModel<?>> sortContext) {
		if (dataModel instanceof SortableDataModel) {
			SortableDataModel<DataModel<?>> sortableModel =
				(SortableDataModel<DataModel<?>>) dataModel;

			TableColumn<?> sortColumn =
				(TableColumn<?>) sortContext.getSortColumn();

			sortableModel.removeSorting();
			sortableModel.setSortDirection(
				sortColumn.getColumnDefinition().getId(),
				sortContext.getSortDir() == SortDir.ASC ?
				SortDirection.ASCENDING :
				SortDirection.DESCENDING);

			materialTable.update();
		}
	}

	/**
	 * A {@link DataSource} implementation that retrieves the data from a data
	 * model.
	 *
	 * @author eso
	 */
	public static class DataModelDataSource
		implements DataSource<DataModel<?>> {

		private DataModel<DataModel<?>> dataModel;

		/**
		 * Creates a new instance.
		 *
		 * @param dataModel The underlying data model
		 */
		@SuppressWarnings("unchecked")
		public DataModelDataSource(DataModel<DataModel<?>> dataModel) {
			this.dataModel = dataModel;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void load(LoadConfig<DataModel<?>> loadConfig,
			LoadCallback<DataModel<?>> loadCallback) {
			if (dataModel instanceof RemoteDataModel) {
				@SuppressWarnings("unchecked")
				RemoteDataModel<DataModel<?>> remoteModel =
					(RemoteDataModel<DataModel<?>>) dataModel;

				remoteModel.setWindow(loadConfig.getOffset(),
					loadConfig.getLimit(),
					new Callback<RemoteDataModel<DataModel<?>>>() {
						@Override
						public void onError(Throwable error) {
							loadCallback.onFailure(error);
						}

						@Override
						public void onSuccess(
							RemoteDataModel<DataModel<?>> remoteModel) {
							LoadResult<DataModel<?>> result =
								createLoadResult(loadConfig, remoteModel);

							loadCallback.onSuccess(result);
						}
					});
			} else {
				LoadResult<DataModel<?>> result =
					createLoadResult(loadConfig, dataModel);

				loadCallback.onSuccess(result);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean useRemoteSort() {
			return dataModel instanceof SortableDataModel;
		}

		/**
		 * Creates the load result for the display of certain table data.
		 *
		 * @param loadConfig The load configuration
		 * @param model      The model to read the data from
		 * @return The load result
		 */
		LoadResult<DataModel<?>> createLoadResult(
			LoadConfig<DataModel<?>> loadConfig,
			DataModel<DataModel<?>> model) {
			int offset = loadConfig.getOffset();
			int limit =
				Math.min(loadConfig.getLimit(), model.getElementCount());

			List<DataModel<?>> data = new ArrayList<>(limit);

			limit += offset;

			for (int i = offset; i < limit; i++) {
				data.add(model.getElement(i));
			}

			return new LoadResult<>(data, offset, model.getElementCount());
		}
	}

	/**
	 * A data table that supports infinite data scrolling.
	 *
	 * @author eso
	 */
	public static class GewtMaterialInfiniteDataTable<T>
		extends GewtMaterialDataTable<T> implements HasLoader {

		/**
		 * Creates a new instance with a dummy data source.
		 */
		public GewtMaterialInfiniteDataTable() {
			super(new InfiniteDataView<>(10, new DataSource<T>() {
				@Override
				public void load(LoadConfig<T> loadConfig,
					LoadCallback<T> callback) {
				}

				@Override
				public boolean useRemoteSort() {
					return false;
				}
			}));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getLoaderBuffer() {
			return ((InfiniteDataView<T>) view).getLoaderBuffer();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getLoaderDelay() {
			return ((InfiniteDataView<T>) view).getLoaderDelay();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isLoading() {
			return ((InfiniteDataView<T>) view).isLoading();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setLoaderBuffer(int loaderBuffer) {
			((InfiniteDataView<T>) view).setLoaderBuffer(loaderBuffer);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setLoaderDelay(int loaderDelay) {
			((InfiniteDataView<T>) view).setLoaderDelay(loaderDelay);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void updateDataSource(DataSource<T> dataSource) {
			super.updateDataSource(dataSource);
		}
	}

	/**
	 * Extended material data table.
	 *
	 * @author eso
	 */
	static class GewtMaterialDataTable<T> extends MaterialDataTable<T> {

		/**
		 * Creates a new instance with a default view.
		 */
		public GewtMaterialDataTable() {
		}

		/**
		 * Creates a new instance with a specific data view.
		 *
		 * @param dataView The data view
		 */
		public GewtMaterialDataTable(DataView<T> dataView) {
			super(dataView);
		}

		/**
		 * Returns the selection.
		 *
		 * @return The selection
		 */
		public T getSelection() {
			T selection = null;

			if (getSelectionType() == SelectionType.NONE) {
				Element[] rowElements = getScaffolding()
					.getTable()
					.getJsElement()
					.find("tr.data-row")
					.toArray();

				for (Element rowElement : rowElements) {
					if (JQuery.$(rowElement).hasClass("selected")) {
						selection = getModelByRowElement(rowElement);

						break;
					}
				}
			} else {
				List<T> selectedRows = getSelectedRowModels(false);

				selection = selectedRows.isEmpty() ? null :
				            selectedRows.get(0);
			}

			return selection;
		}

		/**
		 * Updates this table by refreshing the view.
		 */
		public void update() {
			DataView<T> view = getView();

			if (view.isSetup()) {
				view.setRedraw(true);
				view.refresh();
			}
		}

		/**
		 * Must be implemented by subclasses to update the source for table
		 * data.
		 *
		 * @param dataSource The new data source
		 */
		public void updateDataSource(DataSource<T> dataSource) {
			setDataSource(dataSource);
		}

		/**
		 * Copied from {@link AbstractDataView} to find the selection when the
		 * {@link SelectionType} is NONE.
		 *
		 * @param rowElement The row element
		 * @return The data model of the row or NULL for none
		 */
		T getModelByRowElement(Element rowElement) {
			for (RowComponent<T> row : getView().getRows()) {
				if (row.isRendered() &&
					row.getWidget().getElement().equals(rowElement)) {
					return row.getData();
				}
			}

			return null;
		}
	}

	/**
	 * Extended material data table.
	 *
	 * @author eso
	 */
	static class GewtMaterialPagingDataTable<T>
		extends GewtMaterialDataTable<T> {

		private MaterialDataPager<T> pager = null;

		/**
		 * Creates a new instance.
		 */
		public GewtMaterialPagingDataTable() {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void updateDataSource(DataSource<T> dataSource) {
			super.updateDataSource(dataSource);

			if (pager == null) {
				pager = new MaterialDataPager<>(this, dataSource);

				pager.setLimitOptions(5, 10, 20, 25, 50);
				add(pager);
			} else {
				pager.setDataSource(dataSource);
			}
		}
	}

	/**
	 * The base class for indexed columns.
	 *
	 * @author eso
	 */
	abstract class TableColumn<C> extends Column<DataModel<?>, C> {

		private ColumnDefinition columnDef;

		/**
		 * Creates a new instance.
		 *
		 * @param columnDef The column definition
		 * @param cell      The cell for rendering values
		 * @param index     The column index
		 */
		public TableColumn(ColumnDefinition columnDef, Cell<C> cell,
			int index) {
			super(cell);

			this.columnDef = columnDef;

			SortDirection sortDirection =
				columnDef.getProperty(SORT_DIRECTION, null);

			if (sortDirection != null) {
				autoSort(true);
				defaultSortAscending(sortDirection == SortDirection.ASCENDING);
			}

			sortable(columnDef.hasFlag(StyleProperties.SORTABLE));
			name(context.expandResource(columnDef.getTitle()));
		}

		/**
		 * Returns the columnDef value.
		 *
		 * @return The columnDef value
		 */
		public final ColumnDefinition getColumnDefinition() {
			return columnDef;
		}

		/**
		 * Returns the raw (unparsed) object value from a row data model.
		 *
		 * @param rowData The row data model
		 * @return The raw value
		 */
		protected final Object getRawValue(DataModel<?> rowData) {
			return rowData.getElement(getIndex());
		}
	}

	/**
	 * A column that renders date values.
	 *
	 * @author eso
	 */
	class DateColumn extends TableColumn<Date> {

		/**
		 * Creates a new instance.
		 *
		 * @param columnDefinition The column definition
		 * @param index            The column index
		 */
		public DateColumn(ColumnDefinition columnDefinition, int index) {
			super(columnDefinition,
				new DateCell(getDateTimeFormat(columnDefinition)), index);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Date getValue(DataModel<?> rowData) {
			Object rawValue = getRawValue(rowData);
			Date date = null;

			if (rawValue != null) {
				date = new Date(Long.parseLong(rawValue.toString()));
			}

			return date;
		}
	}

	/**
	 * A column that renders date values.
	 *
	 * @author eso
	 */
	class TextColumn extends TableColumn<String> {

		/**
		 * Creates a new instance.
		 *
		 * @param columnDefinition The column definition
		 * @param index            The column index
		 */
		public TextColumn(ColumnDefinition columnDefinition, int index) {
			super(columnDefinition, new TextCell(), index);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getValue(DataModel<?> rowData) {
			Object rawValue = getRawValue(rowData);

			return rawValue != null ?
			       context.expandResource(rawValue.toString()) :
			       null;
		}
	}
}
