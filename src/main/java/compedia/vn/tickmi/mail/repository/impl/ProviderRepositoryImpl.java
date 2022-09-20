package compedia.vn.tickmi.mail.repository.impl;

import compedia.vn.tickmi.mail.entity.Provider;
import compedia.vn.tickmi.mail.repository.ProviderRepositoryCustom;
import compedia.vn.tickmi.mail.utils.ValueUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Log4j2
public class ProviderRepositoryImpl implements ProviderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Provider> findProviderByUserId(Integer userId) {
        log.debug("Start query find provider by user id");
        Query query = entityManager.createNativeQuery(SQL_findProviderByUserId);
        query.setParameter("userId", userId);
        Provider p = new Provider();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            Object[] obj = result.get(0);
            p.setProviderId(ValueUtil.getIntegerByObject(obj[0]));
            p.setName(ValueUtil.getStringByObject(obj[1]));
            p.setTotalTicket(ValueUtil.getIntegerByObject(obj[2]));
            p.setTotalFollower(ValueUtil.getIntegerByObject(obj[3]));
            p.setTotalFollowing(ValueUtil.getIntegerByObject(obj[4]));
            p.setCreateTime(ValueUtil.getTimestampByObject(obj[5]));
            p.setRegisterPackageId(ValueUtil.getIntegerByObject(obj[6]));
        }
        return Optional.of(p);
    }

    private static String SQL_findProviderByUserId = "select p.* +" +
            "   from PROVIDER p +" +
            "      inner join SYS_USER sysUser on p.PROVIDER_ID = sysUser.PROVIDER_ID +" +
            "   where sysUser.USER_ID = :userId";
}
