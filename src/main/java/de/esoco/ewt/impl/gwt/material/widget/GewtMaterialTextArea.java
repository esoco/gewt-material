package de.esoco.ewt.impl.gwt.material.widget;

import de.esoco.ewt.component.TextArea.IsTextArea;

import gwt.material.design.client.ui.MaterialTextArea;

/********************************************************************
 * A {@link MaterialTextArea} subclass that also implements the interface
 * {@link IsTextArea}.
 *
 * @author eso
 */
public class GewtMaterialTextArea extends MaterialTextArea
	implements IsTextArea
{
	//~ Methods ------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getCursorPos()
	{
		return asGwtValueBoxBase().getCursorPos();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public String getSelectedText()
	{
		return asGwtValueBoxBase().getSelectedText();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadOnly()
	{
		return asGwtValueBoxBase().isReadOnly();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setCharacterWidth(int nColumns)
	{
		// TODO: check if possible with ValueBoxBase
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setCursorPos(int nPosition)
	{
		asGwtValueBoxBase().setCursorPos(nPosition);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setReadOnly(boolean bReadOnly)
	{
		asGwtValueBoxBase().setReadOnly(bReadOnly);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setSelectionRange(int nStart, int nLength)
	{
		asGwtValueBoxBase().setSelectionRange(nStart, nLength);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setVisibleLength(int nColumns)
	{
		// TODO: check if possible with ValueBoxBase
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setVisibleLines(int nRows)
	{
		// TODO: check if possible with ValueBoxBase
	}
}