package de.jgoldhammer.alfresco.jscript.rules;

import com.google.common.base.Preconditions;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.rule.Rule;
import org.alfresco.service.cmr.rule.RuleService;

import java.util.List;

/**
 * wrapper around the ruleservice of alfresco.
 *
 * Created by jgoldhammer on 06.11.16.
 */
public class ScriptRulesService extends BaseScopableProcessorExtension {

	RuleService ruleService;

	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}

	/**
	 * checks wheter ruleservice is enabled
	 * @return true if ruleservice is enabled, false if not.
	 */
	public boolean isEnabled(){
		return ruleService.isEnabled();
	}

	/**
	 * Enable rules for the current thread.
	 */
	public void enableRules(){
		ruleService.enableRules();
	}

	/**
	 * Disable rules for the current thread
	 */
	public void disableRules(){
		ruleService.disableRules();
	}

	/**
	 * enable rules for a certain node.
	 * @param node
	 */
	public void enableRules(ScriptNode node){
		Preconditions.checkNotNull(node);
		ruleService.enableRules(node.getNodeRef());
	}

	public void enableRule(Rule rule){
		ruleService.enableRule(rule);
	}

	/**
	 * disable all rules for a certain node via ScriptNode
	 * @param node
	 */
	public void disableRules(ScriptNode node){
		disableRules(node.getNodeRef());
	}

	/**
	 * disable all rules for a certain node via Noderef
	 * @param node
	 */
	public void disableRules(NodeRef node){
		Preconditions.checkNotNull(node);
		ruleService.disableRules(node);
	}

	/**
	 * disable all rules for a certain node via String
	 * @param node
	 */
	public void disableRules(String node){
		Preconditions.checkNotNull(node);
		Preconditions.checkArgument(NodeRef.isNodeRef(node));
		disableRules(new NodeRef(node));
	}

	/**
	 * checks whether the rules are enabled for a script node
	 * @param node
	 */
	public boolean rulesEnabled(ScriptNode node){
		Preconditions.checkNotNull(node);
		return rulesEnabled(node.getNodeRef());
	}


	/**
	 * checks whether the rules are enabled for a node (via noderef)
	 * @param node
	 */
	public boolean rulesEnabled(NodeRef node){
		Preconditions.checkNotNull(node);
		return ruleService.rulesEnabled(node);
	}

	/**
	 * checks whether the rules are enabled for a node (via string)
	 * @param node
	 */
	public boolean rulesEnabled(String node){
		Preconditions.checkNotNull(node);
		Preconditions.checkArgument(NodeRef.isNodeRef(node));
		return rulesEnabled(new NodeRef(node));
	}

	public List<Rule> getRules(ScriptNode node){
		return ruleService.getRules(node.getNodeRef());
	}

	/**
	 * checks whether the node has rules attached
	 * @param node
	 * @return true if there are any rules, false if not.
	 */
	public boolean hasRules(ScriptNode node){
		return ruleService.hasRules(node.getNodeRef());
	}

	/**
	 * checks whether the node has any direct rules attached
	 * @param node
	 * @return true if there are any direct rules, false if not.
	 */
	public boolean hasDirectRules(ScriptNode node){
		return ruleService.getRules(node.getNodeRef(), false).size() >0;
	}

	/**
	 * disables a certain rule for now.
	 */
	public void disableRule(Rule rule){
		ruleService.disableRule(rule);
	}

	/**
	 * get back the number of rules of a node.
	 * @param node
	 * @return
	 */
	public int countRules(ScriptNode node){
		Preconditions.checkNotNull(node);
		return ruleService.countRules(node.getNodeRef());
	}

	/**
	 * get back the number of rules of a node.
	 * @param node
	 * @return
	 */
	public int countRules(NodeRef node){
		Preconditions.checkNotNull(node);
		return ruleService.countRules(node);
	}

	/**
	 * get back the number of rules of a node.
	 * @param node
	 * @return number of rules attached
	 */
	public int countRules(String node){
		Preconditions.checkNotNull(node);
		Preconditions.checkArgument(NodeRef.isNodeRef(node));
		return ruleService.countRules(new NodeRef(node));
	}

	/**
	 * remove all rules for a given noderef
	 * @param node
	 */
	public void removeAllRules(ScriptNode node){
		Preconditions.checkNotNull(node);
		ruleService.removeAllRules(node.getNodeRef());
	}

	/**
	 * remove all rules for a given noderef
	 * @param node
	 */
	public void removeAllRules(NodeRef node){
		Preconditions.checkNotNull(node);
		ruleService.removeAllRules(node);
	}

	/**
	 * remove all rules for a given noderef
	 * @param node
	 */
	public void removeAllRules(String node){
		Preconditions.checkNotNull(node);
		Preconditions.checkArgument(NodeRef.isNodeRef(node));
		ruleService.removeAllRules(new NodeRef(node));
	}

}
