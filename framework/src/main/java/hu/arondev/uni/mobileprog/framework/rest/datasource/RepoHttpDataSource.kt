package hu.arondev.uni.mobileprog.framework.rest.datasource

import android.content.Context
import hu.arondev.uni.mobileprog.core.data.RepoDataSource
import hu.arondev.uni.mobileprog.core.domain.File
import hu.arondev.uni.mobileprog.core.domain.Repo
import hu.arondev.uni.mobileprog.framework.rest.GithubApiClient
import hu.arondev.uni.mobileprog.framework.rest.datasource.converter.FileConverter
import hu.arondev.uni.mobileprog.framework.rest.datasource.converter.RepoConverter
import org.mapstruct.factory.Mappers

class RepoHttpDataSource(context: Context): RepoDataSource {
    private val repoDao = GithubApiClient.getInstance(context).repoDao()
    private val repoConverter = Mappers.getMapper(RepoConverter::class.java)
    private val fileConverter = Mappers.getMapper(FileConverter::class.java)

    override suspend fun searchReposByName(repoName: String, perPage: Int): List<Repo>
        = repoConverter.convertToDomain(repoDao.searchReposByName(repoName, perPage).items)

    override suspend fun getOneRepositoryByFullName(owner: String, repo: String): Repo
        = repoConverter.convertToDomain(repoDao.getRepoByFullName(owner, repo))

    override suspend fun getReposOfUser(user: String): List<Repo>
        = repoConverter.convertToDomain(repoDao.getReposOfUser(user))

    override suspend fun getFilesOfRepo(user: String, repoName: String, path: String): List<File> {
        val fileEntities = repoDao.getFilesOfRepo(user, repoName, path)
        val files = fileConverter.convertToDomain(fileEntities)
        return files.sorted()
    }
}