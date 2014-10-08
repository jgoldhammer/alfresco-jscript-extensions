/**
 *
 */
package de.jgoldhammer.alfresco.jscript.batch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.alfresco.repo.batch.BatchProcessWorkProvider;
import org.alfresco.service.cmr.repository.NodeRef;


/**
 * a simple list based batch process work provider.
 *
 * @author Jens Goldhammer
 * @param <T>
 *
 */
public class SimpleListWorkProvider implements BatchProcessWorkProvider<Collection<NodeRef>> {

    private List<NodeRef> simpleList;
    private final int collectSize;
	private final int size;
	private int index = 0;

    /**
     *
     */
    public SimpleListWorkProvider(List<NodeRef> simpleList, final int listSize) {
        this.simpleList = simpleList;
		this.collectSize = listSize;
		this.size = this.simpleList.size();
    }

    public int getTotalEstimatedWorkSize() {
        return simpleList.size();
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized Collection<Collection<NodeRef>> getNextWork() {
		final Collection<Collection<NodeRef>> items;
		if (this.size > this.index) {
			items = new ArrayList<Collection<NodeRef>>();
			List<NodeRef> nodes = new ArrayList<NodeRef>();
			items.add(nodes);
			for (int size = 0; size < this.collectSize && this.size > this.index; this.index++, size++) {
				nodes.add(this.simpleList.get(this.index));
			}
		} else {
			items = new ArrayList<Collection<NodeRef>>();
		}
		return items;
	}

}
