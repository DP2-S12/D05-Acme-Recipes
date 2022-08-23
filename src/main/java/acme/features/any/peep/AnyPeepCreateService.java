package acme.features.any.peep;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peep.Peep;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractCreateService;

@Service
public class AnyPeepCreateService implements AbstractCreateService<Any, Peep>{
	
	@Autowired
	protected AnyPeepRepository repository;
	
	@Override
	public boolean authorise(final Request<Peep> request) {
		assert request != null;
		return true;
	}
	
	@Override
	public Peep instantiate(final Request<Peep> request) {
		assert request != null;
		Peep res;
		res = new Peep();
		return res;
	}
	
	@Override
	public void bind(final Request<Peep> request, final Peep entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		Date creationTime;
		Calendar calendar;
		creationTime = new Date();
		calendar = Calendar.getInstance();
		creationTime = calendar.getTime();
		request.bind(entity, errors, "writer", "pieceOfText","email","heading");
		entity.setInstantionMoment(creationTime);
		
	}
	
	@Override
	public void validate(final Request<Peep> request, final Peep entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final boolean confirm = request.getModel().getBoolean("confirm");
		errors.state(request, confirm, "confirm", "any.peep.accept.error");

	}
	
	@Override
	public void unbind(final Request<Peep> request, final Peep entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "writer", "pieceOfText","email","heading");
		model.setAttribute("confirm", false);
	}

	@Override
	public void create(final Request<Peep> request, final Peep entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}


}