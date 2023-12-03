package ru.dubna.kts.models.auth.validators;

public abstract class ValidatorChain {
	protected ValidatorChain next;

	public ValidatorChain setNext(ValidatorChain next) {
		this.next = next;
		return next;
	}

	public abstract void validate(Object target);
}
