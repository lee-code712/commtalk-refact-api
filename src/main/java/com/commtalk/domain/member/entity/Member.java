package com.commtalk.domain.member.entity;

import com.commtalk.common.entity.BaseEntity;
import com.commtalk.domain.member.dto.JoinDTO;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private MemberPassword password;

    @Setter
    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    private String phone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_role_id", nullable = false)
    private MemberRole role;

    public static Member create(JoinDTO joinDto, MemberRole role) {
        return Member.builder()
                .nickname(joinDto.getNickname())
                .memberName(joinDto.getUsername())
                .email(joinDto.getEmail())
                .phone(joinDto.getPhone())
                .role(role)
                .build();
    }

}