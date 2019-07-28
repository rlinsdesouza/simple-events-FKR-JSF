package br.edu.ifpb.simpleevents.security;



import java.util.Arrays;
import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;


@ApplicationScoped
public class ValidaLogin implements IdentityStore {
	
	 @Override
	    public CredentialValidationResult validate(Credential credential) {
	        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
	        if (login.getCaller().equals("admin@teste") 
	                       && login.getPasswordAsString().equals("admin")) {
	            return new CredentialValidationResult("admin", new HashSet<>(Arrays.asList("ADMIN")));
	        } else if (login.getCaller().equals("user@mail.com") 
	                       && login.getPasswordAsString().equals("USER1234")) {
	            return new CredentialValidationResult("user", new HashSet<>(Arrays.asList("USER")));
	        } else {
	            return CredentialValidationResult.NOT_VALIDATED_RESULT;
	        }
	    }
}


