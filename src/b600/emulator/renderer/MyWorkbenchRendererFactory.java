package b600.emulator.renderer;

import org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.renderers.swt.WorkbenchRendererFactory;

@SuppressWarnings("restriction")
public class MyWorkbenchRendererFactory extends WorkbenchRendererFactory {

	private MyWBWRenderer mywbwRenderer;

	@Override
	public AbstractPartRenderer getRenderer(MUIElement uiElement, Object parent) {
		if (uiElement instanceof MWindow) {
			if (mywbwRenderer == null) {
				mywbwRenderer = new MyWBWRenderer();
				super.initRenderer(mywbwRenderer);
			}
			return mywbwRenderer;
		}
		return super.getRenderer(uiElement, parent);
	}
}
