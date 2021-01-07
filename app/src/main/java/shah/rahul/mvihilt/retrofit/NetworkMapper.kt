package shah.rahul.mvihilt.retrofit

import shah.rahul.mvihilt.model.Blog
import shah.rahul.mvihilt.util.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : EntityMapper<BlogNetworkEntity, Blog>
{
    override fun mapFromEntity(entity: BlogNetworkEntity): Blog {
        return Blog(
            id = entity.id,
            title = entity.title,
            image = entity.image,
            body = entity.body,
            category = entity.category
        )
    }

    override fun mapToEntity(domainModel: Blog): BlogNetworkEntity {
        return BlogNetworkEntity(
            id = domainModel.id,
            title = domainModel.title,
            image = domainModel.image,
            body = domainModel.body,
            category = domainModel.category
        )
    }

    fun mapFromEntityList(entities: List<BlogNetworkEntity>): List<Blog> {
        return entities.map { mapFromEntity(it) }
    }
}