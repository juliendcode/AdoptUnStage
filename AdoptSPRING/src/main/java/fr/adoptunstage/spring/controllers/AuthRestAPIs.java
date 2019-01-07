package fr.adoptunstage.spring.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.adoptunstage.spring.message.request.LoginForm;
import fr.adoptunstage.spring.message.response.JwtResponse;

import fr.adoptunstage.spring.repos.RoleRepository;
import fr.adoptunstage.spring.repos.UserRepository;
import fr.adoptunstage.spring.security.jwt.JwtProvider;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;


	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}
<<<<<<< HEAD

	@PostMapping("/signup/entreprise")
	public ResponseEntity<?> registerEntreprise(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		Entreprise user = new Entreprise(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getRaisonSociale(), signUpRequest.getSecteur(), signUpRequest.getStatut(), signUpRequest.getSiteWeb(), signUpRequest.getAdresse(), signUpRequest.getVille(), signUpRequest.getCodePostal(), signUpRequest.getLogo(), signUpRequest.getPrenom(), signUpRequest.getFonction(), signUpRequest.getTel());

		Set<String> strRoles = new HashSet<String>();
		strRoles.add("entreprise");
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
				case "entreprise":
					Role entrepriseRole = roleRepository.findByName(RoleName.ROLE_ENTREPRISE)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
					roles.add(entrepriseRole);

					break;
				default:
					break;
			}
		});

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}

	@PostMapping("/signup/stagiaire")
	public ResponseEntity<?> registerStagiaire(@Valid @RequestBody SignUpFormStagiaire signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		Stagiaire user = new Stagiaire(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getPrenom(), signUpRequest.getEtablissement(), signUpRequest.getVille(), signUpRequest.getCodePostal(), signUpRequest.getTel());

		Set<String> strRoles = new HashSet<String>();
		strRoles.add("stagiaire");
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
				case "stagiaire":
					Role stagiaireRole = roleRepository.findByName(RoleName.ROLE_STAGIAIRE)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
					roles.add(stagiaireRole);

					break;
				default:
					break;
			}
		});

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}




=======
	
>>>>>>> 7a357d1b24b87b1a503e3066fbe2151f50aee014
}