package org.cos30018.hets.logic.retailer.behaviour;

import org.cos30018.hets.logic.retailer.RetailerAgent;
import org.cos30018.hets.negotiation.Offer;

import jade.core.behaviours.Behaviour;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SSIteratedContractNetResponder;
import jade.proto.SSResponderDispatcher;

public class RetailerResponderBehaviour extends SSResponderDispatcher {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960196745935851735L;

	private RetailerAgent retailerAgent;

	public RetailerResponderBehaviour(RetailerAgent retailerAgent) {
		super(retailerAgent, MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CFP),
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_ITERATED_CONTRACT_NET)));
		this.retailerAgent = retailerAgent;
	}

	@Override
	protected Behaviour createResponder(ACLMessage initiationMsg) {
		return new SSIteratedContractNetResponder(getAgent(), initiationMsg) {

			@Override
			protected ACLMessage handleCfp(ACLMessage cfp)
					throws RefuseException, FailureException, NotUnderstoodException {
				retailerAgent.addNegotiationMessage(cfp);

				try {
					Offer incomingOffer = (Offer) cfp.getContentObject();
					Offer counterOffer = retailerAgent.getStrategy().getCounterOffer(incomingOffer);

					ACLMessage reply = cfp.createReply();
					reply.setSender(getAgent().getAID());
					reply.setPerformative(ACLMessage.PROPOSE);
					reply.setContentObject(counterOffer);

					retailerAgent.addNegotiationMessage(reply);
					return reply;
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}

				return null;
			}

			@Override
			protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept)
					throws FailureException {
				retailerAgent.addNegotiationMessage(accept);
				retailerAgent.getStrategy().reset(retailerAgent.getTariff().getPrice(0, 0));

				try {
					ACLMessage reply = accept.createReply();
					reply.setContentObject(propose.getContentObject());
					reply.setPerformative(ACLMessage.INFORM);
					retailerAgent.addNegotiationMessage(accept);
					return reply;
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}

				return null;
			}

			@Override
			protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
				retailerAgent.addNegotiationMessage(reject);
				retailerAgent.getStrategy().reset(retailerAgent.getTariff().getPrice(0, 0));
			}
		};
	}
}