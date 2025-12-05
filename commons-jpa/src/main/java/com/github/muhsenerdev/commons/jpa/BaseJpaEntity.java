
package com.github.muhsenerdev.commons.jpa;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseJpaEntity<ID extends Serializable> {

    public abstract ID getId();

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @CreatedBy
    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = true)
    private String updatedBy;

    /**
     * JPA Entity'leri için Best-Practice Equals Metodu.
     * <p>
     * Mantık: 1. Referans aynıysa (memory adresi) -> Eşittir. 2. Sınıf tipleri
     * uyumsuzsa -> Eşit değildir. 3. ID'ler null ise -> Eşit değildir
     * (Transient/Yeni nesneler birbirine eşit olamaz). 4. ID'ler aynıysa ->
     * Eşittir.
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;

        // Hibernate Proxy safe class check
        // Hibernate bazen nesneyi Proxy olarak getirir (Lazy Loading).
        // Bu durumda o.getClass() != this.getClass() hatası alırsın.
        // Doğrusu effective class (yani asıl entity sınıfı) kontrolüdür.
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass)
            return false;

        BaseJpaEntity<?> that = (BaseJpaEntity<?>) o;

        // İki yeni (kaydedilmemiş, ID'si null) nesne ASLA eşit değildir.
        // Sadece ID'leri varsa ve ID'leri eşitse eşittir.
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    /**
     * JPA Entity'leri için Best-Practice HashCode.
     * <p>
     * Neden Sabit (Constant) veya Class-Based HashCode? Çünkü Entity bir Set/Map
     * içine konulduğunda (henüz ID'si yokken), sonrasında save() edilip ID alırsa
     * hashCode değişmemelidir. Eğer hashCode ID'ye bağlı olursa ve değişirse, Set
     * içindeki nesne kaybolur (memory leak).
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

}
