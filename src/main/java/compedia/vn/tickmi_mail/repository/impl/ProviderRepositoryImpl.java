package compedia.vn.tickmi_mail.repository.impl;

import compedia.vn.tickmi_mail.entity.Provider;
import compedia.vn.tickmi_mail.repository.ProviderRepositoryCustom;
import compedia.vn.tickmi_mail.utils.ValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class ProviderRepositoryImpl implements ProviderRepositoryCustom {

    private final static Logger logger = LoggerFactory.getLogger(ProviderRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Provider> findProviderByUserId(Long userId) {
        logger.debug("Repository find provider by user!");
        StringBuilder sb = new StringBuilder();
        sb.append("select p.*" +
                " from PROVIDER p" +
                "    inner join SYS_USER sysUser on p.PROVIDER_ID = sysUser.PROVIDER_ID" +
                " where sysUser.USER_ID = :userId");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userId", userId);
        Provider p = new Provider();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            Object[] obj = result.get(0);
            p.setProviderId(ValueUtil.getLongByObject(obj[0]));
            p.setName(ValueUtil.getStringByObject(obj[1]));
            p.setTotalTicket(ValueUtil.getIntegerByObject(obj[2]));
            p.setTotalFollower(ValueUtil.getIntegerByObject(obj[3]));
            p.setTotalFollowing(ValueUtil.getIntegerByObject(obj[4]));
            p.setCreateTime(ValueUtil.getTimestampByObject(obj[5]));
            p.setRegisterPackageId(ValueUtil.getLongByObject(obj[6]));
        }
        return Optional.of(p);
    }
}
