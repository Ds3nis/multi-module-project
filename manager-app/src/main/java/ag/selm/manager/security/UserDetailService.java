//package ag.selm.manager.security;
//
//import ag.selm.manager.entity.Authority;
//import ag.selm.manager.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return this.userRepository.findUserByUsername(username)
//                        .map(user -> User.builder()
//                                .username(user.getUsername())
//                                .password(user.getPassword())
//                                .authorities(user.getAuthorities().stream().map(Authority::toString).map(SimpleGrantedAuthority::new).toList())
//                                .build()).orElseThrow(() -> new UsernameNotFoundException("Uživatel %s nebyl nalezen".formatted(username)));
//    }
//}
