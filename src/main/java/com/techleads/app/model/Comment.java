package com.techleads.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends AuditModel{

    @EmbeddedId
    private CommentKey commentKey;

    @NotNull
    @Lob
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "post_id, post_type", nullable = false)
    @JoinColumns({
            @JoinColumn(name = "post_id", referencedColumnName = "post_id",
                    insertable = false, updatable = false),
            @JoinColumn(name = "post_type", referencedColumnName = "post_type",
                    insertable = false, updatable = false)
    })
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
