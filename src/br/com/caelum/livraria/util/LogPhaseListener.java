package br.com.caelum.livraria.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LogPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforePhase(PhaseEvent event) {
//		System.out.println("FASE: "+ event.getPhaseId());

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
