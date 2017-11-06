package de.jgoldhammer.alfresco.jscript.attributes;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.service.cmr.attributes.AttributeService;
import org.mozilla.javascript.NativeObject;

/**
 * the class wraps the attributeservice of Alfresco and allows to get, create new attributes and remove them.
 *
 * @see AttributeService for more details.
 */
public class ScriptAttributeService extends BaseScopableProcessorExtension {

        protected AttributeService attributeService;

        public void setAttributeService(AttributeService attributeService) {
			this.attributeService = attributeService;
        }

        public Object getAttribute(String ...keys) {
                return attributeService.getAttribute((Serializable[]) keys);
        }

        public boolean exists(String ...keys) {
              return attributeService.exists((Serializable[]) keys);
        }

        public void createAttribute(String value, String ...keys) {
                attributeService.createAttribute(value, (Serializable[]) keys);
        }

        public void createAttribute(Number value, String ...keys) {
                attributeService.createAttribute(value, (Serializable[]) keys);
        }

        public void createAttribute(Boolean value, String ...keys) {
                attributeService.createAttribute(value, (Serializable[]) keys);
        }

        public void setAttribute(String value, String ...keys) {
                attributeService.setAttribute(value, (Serializable[]) keys);
        }

        public void setAttribute(Number value, String ...keys) {
                attributeService.setAttribute(value, (Serializable[]) keys);
        }

        public void setAttribute(Boolean value, String ...keys) {
                attributeService.setAttribute(value, (Serializable[]) keys);
        }

        public void removeAttribute(String ...keys)  {
                attributeService.removeAttribute((Serializable[]) keys);
        }

        public void removeAttributes(final String ...keys)  {
                // remove support for leaving the middle key empty!
                if (keys.length == 3 && keys[1]==null) {

                        final Set<Serializable[]> keysToDelete = new HashSet<Serializable[]>();

                        attributeService.getAttributes(new AttributeService.AttributeQueryCallback() {

                                @Override
                                public boolean handleAttribute(Long id, Serializable value, Serializable[] foundKeys) {
                                        if (foundKeys.length == 3 && keys[0].equals(foundKeys[0]) && keys[2].equals(foundKeys[2])) {
                                                keysToDelete.add(foundKeys);
                                        }
                                        return true;
                                }
                        }, new Serializable[] {keys[0]});

                        for (Serializable[] keyTuple : keysToDelete) {
                                attributeService.removeAttribute(keyTuple);
                        }
                }
                else {
                        attributeService.removeAttributes((Serializable[]) keys);
                }
        }

        public NativeObject getAttributes(String ...keys) {

                final NativeObject root = new NativeObject();
                final int levelSpecified = keys.length;

                attributeService.getAttributes(new AttributeService.AttributeQueryCallback() {

                        Map<String, NativeObject> subObjects = new HashMap<String, NativeObject>();

                        @Override
                        public boolean handleAttribute(Long id, Serializable value, Serializable[] keys) {
                                Serializable[] relevantKeys = Arrays.copyOfRange(keys, levelSpecified, keys.length);

                                if (relevantKeys.length == 0) {
                                        root.defineProperty("value", value, NativeObject.READONLY);
                                }
                                else if (relevantKeys.length == 1) {
                                        root.defineProperty(relevantKeys[0].toString(), value, NativeObject.READONLY);
                                }
                                else if (relevantKeys.length == 2) {
                                        NativeObject inner = subObjects.get(relevantKeys[0].toString());
                                        if (inner == null) {
                                                inner = new NativeObject();
                                                subObjects.put(relevantKeys[0].toString(), inner);
                                        }
                                        inner.defineProperty(relevantKeys[1].toString(), value, NativeObject.READONLY);
                                }
                                else {
                                        throw new AlfrescoRuntimeException("Result object with more than one key hierarchie are currently not supported.");
                                }

                                // add all the level2 objects to the root objects
                                for (Map.Entry<String, NativeObject> so : subObjects.entrySet()) {
                                        root.defineProperty(so.getKey(), so.getValue(), NativeObject.READONLY);
                                }

                                return true;
                        }
                }, (Serializable[])keys);

                return root;
        }

}